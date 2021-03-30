pragma solidity ^0.5.16;
import "../roles/Roles.sol";
import "../flow/Flow.sol";

contract StepManager {
    
    // Variables
    struct Change {
        bytes32 accreditDocument;
        bytes32 revokeDocument;
        bytes32 accreditComment;
        address user;
        bytes32 roleUser;
        bool isFinal;
        uint timestamp;
    }
    struct Project {
        Flow flow;
        Roles roles;
        bool finalized;
        bool initialized;
        bytes32 currentStep;
        address creator;
        mapping (bytes32 => bool) stepCompleted;
        // It contains the information of changes according to a type of document: document type => array struct Change
        mapping (bytes32 => Change[]) changes;
    }
    // It contains the information of a project according to its identifier: Project code => Project
    mapping (bytes32 => Project) public projects;
    
    // Events
    event ProjectInitialized(address user, bytes32 projectCode, bytes32 firstStep, address flowContract, address rolesContract);
    event ProjectChangedStep(bytes32 actualStep, bytes32 newStep, address user);
    event ProjectFinalized(bytes32 projectCode, address user);
    event ChangeSet(address user, bytes32 projectCode, bytes32 documentType, uint total);
    event ChangeFinalized(address user, bytes32 projectCode, bytes32 documentType, bool isFinal);
    
    // Modifiers
    modifier validateChangeStep(bytes32 projectCode, bytes32 targetStep, bytes32 userRole) {
        require(!projects[projectCode].finalized, "you cannot do this: project already closed");
        require(hasValidDocuments(projectCode), "documents required or not finalized");
        require(isValidStepChange(projectCode, targetStep), "you cannot change to this step");
        requireRoleUser(projectCode, userRole, msg.sender, targetStep);
        _;
    }
    
    modifier onlyDocumentTypeRole(bytes32 projectCode, bytes32 documentType, bytes32 userRole) {
        requireDocumentTypeRole(projectCode, documentType, userRole);
        Project storage project = projects[projectCode];
        requireRoleUser(projectCode, userRole, msg.sender, project.currentStep);
        _;
    }
    
    modifier validateRulesToSetDocumentType(bytes32 projectCode, bytes32 documentType, bytes32 step, bytes32 userRole) {
        require(projects[projectCode].flow.existsDocumentKeyInStep(step, documentType), "document key is not present in the step");
        Change[] memory listChanges = projects[projectCode].changes[documentType];
        if (listChanges.length > 0) {
            Change memory change = listChanges[listChanges.length - 1];
            require(!change.isFinal, "you can't make changes: document finalized");
        }
        _;
    }
    
    /**@dev This method is used to initialize a project in the contract using a identifier for the project
     * @param projectCode Project Identifier
     * @param firstStep Initial step with which the project begins. Step is declared in the Flow contract
     * @param flowContract Contract where the possible steps for a process are defined
     * @param rolesContract Address of the contract with the definition of roles by user
     * @param userRole Role valid to init a new project. It must define as authorized role in the step
    */
    function initProject(bytes32 projectCode, bytes32 firstStep, address flowContract, address rolesContract, bytes32 userRole) public {
        Project storage project = projects[projectCode];
        require(!project.initialized, "project already was initialized");
        project.initialized = true;
        project.currentStep = firstStep;
        project.flow = Flow(flowContract);
        require(project.flow.isInitialStep(firstStep), "first step not valid");
        project.roles = Roles(rolesContract);
        requireRoleUser(projectCode, userRole, msg.sender, firstStep);
        project.creator = msg.sender;
        emit ProjectInitialized(msg.sender, projectCode, firstStep, flowContract, rolesContract);
    }
    
    /**@dev Changes the current status of a project to a new step previously defined in the Flow contract
     * @param projectCode Project Identifier
     * @param targetStep Step to which the project will be passed
     * @param userRole Role of the user who will perform the action
    */
    function changeStep(bytes32 projectCode, bytes32 targetStep, bytes32 userRole) public validateChangeStep(projectCode, targetStep, userRole) {
        Project storage project = projects[projectCode];
        emit ProjectChangedStep(project.currentStep, targetStep, msg.sender);
        project.stepCompleted[project.currentStep] = true;
        project.currentStep = targetStep;
        if (project.flow.isFinalStep(targetStep)) {
            project.finalized = true;
            emit ProjectFinalized(projectCode, msg.sender);
        }
    }
    
    /**@dev Upload a list of changes for a document type
     * @param projectCode Project Identifier
     * @param documentType Type of document that will contain the change information
     * @param stepId Step to validate the presence of the document type
     * @param _userRole Role of the user
     * @param accreditDocumentHash Document Hash to accredit
     * @param revokeDocumentHash Document Hash to revoke
     * @param accreditCommentHash Comment hash to accredit
     * @param _isFinal Indicates that no more documents can be added for a certain type
    */
    function setDocumentType(bytes32 projectCode, bytes32 documentType, bytes32 stepId, bytes32 _userRole, bytes32 accreditDocumentHash, bytes32 revokeDocumentHash, bytes32 accreditCommentHash, bool _isFinal) public
    validateRulesToSetDocumentType (projectCode, documentType, stepId, _userRole) {
        if (_isFinal) requireDocumentTypeRole(projectCode, documentType, _userRole);
        Change memory newChange = Change({
            accreditDocument: accreditDocumentHash,
            revokeDocument: revokeDocumentHash,
            accreditComment: accreditCommentHash,
            user: msg.sender,
            roleUser: _userRole,
            isFinal: _isFinal,
            timestamp: now
        });
        saveDocumentType(projectCode, documentType, newChange);
    }
    
    /**@dev Allows more changes to be added for a given type
     * @param projectCode Project Identifier
     * @param documentType Key to use to disable a hash
     * @param userRole Role of the user
    */
    function unfinalizeDocumentType(bytes32 projectCode, bytes32 documentType, bytes32 userRole) public onlyDocumentTypeRole(projectCode, documentType, userRole) {
        Change[] storage listChanges = projects[projectCode].changes[documentType];
        uint lastIndex = listChanges.length - 1;
        require(listChanges[lastIndex].isFinal, "change already open");
        listChanges[lastIndex].isFinal = false;
        emit ChangeFinalized(msg.sender, projectCode, documentType, listChanges[lastIndex].isFinal);
    }
    
    /**@dev Returns the total of changes for a document type
     * @param projectCode Project Identifier
     * @param documentType Key of de document type
    */
    function getDocumentTypeCount(bytes32 projectCode, bytes32 documentType) public view returns (uint) {
        Project storage project = projects[projectCode];
        Change[] memory change = project.changes[documentType];
        return change.length;
    }
    
    /**@dev Return a change struct according to its index in the array of documents by type
     * @param projectCode Project Identifier
     * @param documentType Key of de document type
     * @param index Index where the require document is present
    */
    function getDocumentType(bytes32 projectCode, bytes32 documentType, uint index) public view returns (bytes32, bytes32, bytes32, address, bytes32, bool, uint) {
        Project storage project = projects[projectCode];
        Change memory change = project.changes[documentType][index];
        return (change.accreditDocument, change.revokeDocument, change.accreditComment, change.user, change.roleUser, change.isFinal, change.timestamp);
    }
    
    /**@dev Check if a comment has been credited for a document type
     * @param projectCode Project Identifier
     * @param documentType Key of de document type
     * @param hashComment Comment hash to verify
    */
    function getDocumentTypeByComment(bytes32 projectCode, bytes32 documentType, bytes32 hashComment) public view returns (bytes32, bytes32, bytes32, address, bytes32, bool, uint) {
        uint totalDocuments = getDocumentTypeCount(projectCode, documentType);
        (bool found, uint index) = existsCommentHash(projectCode, documentType, hashComment, totalDocuments);
        require(found, "comment does not exist");
        return getDocumentType(projectCode, documentType, index);
    }
    
    /**@dev Check if a document has been credited for a document type
     * @param projectCode Project Identifier
     * @param documentType Key of de document type
     * @param hashDocument Document hash to verify
    */
    function getDocumentTypeByDocument(bytes32 projectCode, bytes32 documentType, bytes32 hashDocument) public view returns (bytes32, bytes32, bytes32, address, bytes32, bool, uint) {
        uint totalDocuments = getDocumentTypeCount(projectCode, documentType);
        (bool found, uint index) = existsDocumentHash(projectCode, documentType, hashDocument, totalDocuments);
        require(found, "document type does not exist");
        return getDocumentType(projectCode, documentType, index);
    }
    
    /**@dev Check if a comment exists for a document type
     * @param projectCode Project Identifier
     * @param documentType Key of de document type
     * @param hashComment Comment hash to find
     * @param total Total documents assigned to a document type
    */
    function existsCommentHash(bytes32 projectCode, bytes32 documentType, bytes32 hashComment, uint total) internal view returns (bool found, uint index) {
        Project storage project = projects[projectCode];
        found = false;
        for (uint i = 0; i < total; i++) {
            Change memory change = project.changes[documentType][i];
            if (change.accreditComment == hashComment) {
                index = i;
                found = true;
            }
        }
    }
    
    /**@dev Check if a document exists for a document type
     * @param projectCode Project Identifier
     * @param documentType Key of de document type
     * @param hashDocument Comment hash to find
     * @param total Total documents assigned to a document type
    */
    function existsDocumentHash(bytes32 projectCode, bytes32 documentType, bytes32 hashDocument, uint total) internal view returns (bool found, uint index) {
        Project storage project = projects[projectCode];
        found = false;
        for (uint i = 0; i < total; i++) {
            Change memory change = project.changes[documentType][i];
            if (change.accreditDocument == hashDocument || change.revokeDocument == hashDocument) {
                index = i;
                found = true;
            }
        }
    }
    
    /**@dev Returns if certain project has completed a step
     * @param projectCode Project Identifier
     * @param stepId Step to validate
    */
    function isStepCompletedProject(bytes32 projectCode, bytes32 stepId) public view returns (bool) {
        return projects[projectCode].stepCompleted[stepId];
    }
    
    /**@dev Valid if a user has a role
     * @param projectCode Project Identifier
     * @param userRole Role of the user
     * @param user Address of the user
    */
    function requireRoleUser(bytes32 projectCode, bytes32 userRole, address user, bytes32 targetStep) internal view {
        (bool isAuthorized, bool isProjectSpecific) = projects[projectCode].flow.roleAuthorization(targetStep, userRole);
        require(isAuthorized, "role not present in the step");
        if (isProjectSpecific) {
            require(projects[projectCode].roles.hasRoleUser(userRole, user, projectCode), "user does not have this role for this project");
        } else {
            require(projects[projectCode].roles.hasRoleUser(userRole, user), "user does not have this role");
        }
    }
    
    /**@dev Valid if a role is authorized for a document type
     * @param projectCode Project Identifier
     * @param documentType Document type to validate
     * @param userRole Role of the user
    */
    function requireDocumentTypeRole(bytes32 projectCode, bytes32 documentType, bytes32 userRole) internal view {
        Project storage project = projects[projectCode];
        (bool isAuthorized, bool isProjectSpecific) = project.flow.documentRoleAuthorization(project.currentStep, documentType, userRole);
        require(isAuthorized, "role not authorized to set this document type");
        if (isProjectSpecific) {
            require(project.roles.hasRoleUser(userRole, msg.sender, projectCode), "user does not have this role for this project");
        } else {
            require(project.roles.hasRoleUser(userRole, msg.sender), "user does not have this role");
        }
    }
    
    /**@dev Verify that all required documents are present in the current step for a project
     * @param projectCode Project Identifier
    */
    function hasValidDocuments(bytes32 projectCode) public view returns (bool) {
        Project storage project = projects[projectCode];
        bytes32[] memory documentKeys = project.flow.getMandatorydocumentKeysStep(project.currentStep);
        for (uint i = 0; i < documentKeys.length; i++) {
            if (!validateFinalDocumentType(projectCode, documentKeys[i])) {
                return false;
            }
        }
        return true;
    }
    
    /**@dev Verify that a document type is finalized
     * @param projectCode Project Identifier
     * @param documentType Document type to validate
    */
    function validateFinalDocumentType(bytes32 projectCode, bytes32 documentType) internal view returns (bool) {
        Project storage project = projects[projectCode];
        Change[] memory changeList = project.changes[documentType];
        if (changeList.length == 0) return false;
        return changeList[changeList.length - 1].isFinal;
    }
    
    /**@dev Validate that the current step for a project is valid to move to a new step
     * @param projectCode Project Identifier
     * @param newStep Step in which it is verified if the current step for a project is valid
    */
    function isValidStepChange(bytes32 projectCode, bytes32 newStep) internal view returns (bool) {
        Project storage project = projects[projectCode];
        return project.flow.isValidStepChange(project.currentStep, newStep);
    }
    
    /**@dev Add a new document struct to document type array
     * @param projectCode Project Identifier
     * @param documentType Document type to which a new record will be added
     * @param newChange Change struct to add
    */
    function saveDocumentType(bytes32 projectCode, bytes32 documentType, Change memory newChange) internal {
        Project storage project = projects[projectCode];
        project.changes[documentType].push(newChange);
        emit ChangeSet(msg.sender, projectCode, documentType, project.changes[documentType].length);
        if (newChange.isFinal) emit ChangeFinalized(msg.sender, projectCode, documentType, newChange.isFinal);
    }
    
}