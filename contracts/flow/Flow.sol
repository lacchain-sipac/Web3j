pragma solidity ^0.5.16;
import "../identity/MultiOwned.sol";

contract Flow is MultiOwned {
    
    // Variables
    struct Step {
        bytes32[] mandatoryDocumentKeys;
        bytes32[] optionalDocumentKeys;
        mapping (bytes32 => bool) roleInStep;
        // Document type => role => bool
        mapping (bytes32 =>  mapping (bytes32 => bool)) authorizedDocumentRoles;
        // Role => bool
        mapping (bytes32 => bool) isProjectSpecificRole;
        mapping (bytes32 => bool) previousValidSteps;
        bool isFinalStep;
        bool isInitialStep;
    }
    mapping (bytes32 => Step) public steps;
    
    // Events
    event StepAdded(bytes32 step, bytes32[] previousValidSteps, address user);
    event StatusAuthorizedDocumentUser(bytes32 step, bytes32 documentType, bytes32 role, bool status);
    
    constructor() MultiOwned(msg.sender) public {}
    
    /**@dev This method is used to add a new step for a flow defining necessary and optional roles and documents
     * @param stepId Identifier of the step to add
     * @param _mandatoryDocumentKeys Types of documents required for this step. Keys are used to identify each
     * @param _optionalDocumentKeys Types of documents optional for this step
     * @param _authorizedRoles Types of roles authorized to reach this step
     * @param _previousValidSteps Allowed steps that can go to this step
     * @param _projectSpecificRoles Specific roles for a project to set add a new change for a document type and to reach this step
     * @param _isFinalStep Define if a step represents the end of a flow
     * @param _isInitialStep Define if a step its possible to use as a first step in the initialization of a project
    */
    function addStep(bytes32 stepId, bytes32[] memory _mandatoryDocumentKeys, bytes32[] memory _optionalDocumentKeys, bytes32[] memory _authorizedRoles, bytes32[] memory _previousValidSteps , bytes32[] memory _projectSpecificRoles, bool _isFinalStep, bool _isInitialStep) public onlyOwner {
        steps[stepId].mandatoryDocumentKeys = _mandatoryDocumentKeys;
        steps[stepId].optionalDocumentKeys = _optionalDocumentKeys;
        if (_authorizedRoles.length > 0) {
            setStatusAuthorizedRolesStep(stepId, _authorizedRoles, true);
        }
        if (_previousValidSteps.length > 0) {
            setPreviousValidStep(stepId, _previousValidSteps, true);
        }
        if (_projectSpecificRoles.length > 0) {
            setProjectSpecificRoles(stepId, _projectSpecificRoles, true);
        }
        steps[stepId].isFinalStep = _isFinalStep;
        steps[stepId].isInitialStep = _isInitialStep;
        emit StepAdded(stepId, _previousValidSteps, msg.sender);
    }
    
    /**@dev This method is used to authorize or not a list of user roles to reach this stage. Roles used to validate which role can change to this step
     * @param stepId Identifier of the step where the roles are going to be set
     * @param roles List of roles
     * @param status Status to be established for each role
    */
    function setStatusAuthorizedRolesStep(bytes32 stepId, bytes32[] memory roles, bool status) public onlyOwner {
        for (uint i = 0; i < roles.length; i++) {
            steps[stepId].roleInStep[roles[i]] = status;
        }
    }
    
    /**@dev This method is used to establish which are the valid steps to reach or not the current step
     * @param stepId Identifier of the step where the previous steps are going to be set
     * @param previousValidSteps Steps that can go or not to this step
     * @param status Status to be established
    */
    function setPreviousValidStep(bytes32 stepId, bytes32[] memory previousValidSteps, bool status) public onlyOwner {
        for (uint i = 0; i < previousValidSteps.length; i++) {
            steps[stepId].previousValidSteps[previousValidSteps[i]] = status;
        }
    }
    
    /**@dev This method is used to define roles that are specific to a project for the step change or load change by document type
     * @param stepId Identifier of the step where permissions are going to be set
     * @param _projectSpecificRoles List of roles to be established
     * @param status Status to be established
    */
    function setProjectSpecificRoles(bytes32 stepId, bytes32[] memory _projectSpecificRoles, bool status) public onlyOwner {
        for (uint i = 0; i < _projectSpecificRoles.length; i++) {
            steps[stepId].isProjectSpecificRole[_projectSpecificRoles[i]] = status;
        }
    }
    
    /**@dev This method is used to establish which roles can set or not a hash for a document type
     * @param stepId Identifier of the step where permissions are going to be set
     * @param documentType Key that identifies the document type
     * @param userRoles List of users roles to authorized
     * @param status Status to be established
    */
    function setStatusAuthorizedDocumentRole(bytes32 stepId, bytes32 documentType, bytes32[] memory userRoles, bool status) public onlyOwner {
        for (uint i = 0; i < userRoles.length; i++) {
            steps[stepId].authorizedDocumentRoles[documentType][userRoles[i]] = status;
            emit StatusAuthorizedDocumentUser(stepId, documentType, userRoles[i], status);
        }
    }
    
    /**@dev This method is used to validate if a given role is required to upload a change for a document type at the stage and project level
     * @param stepId Identifier of the step where the document type is present
     * @param documentType Key that identifies the document type
     * @param userRole User role
    */
    function documentRoleAuthorization(bytes32 stepId, bytes32 documentType, bytes32 userRole) public view returns (bool isAuthorized, bool isProjectSpecific) {
        isAuthorized = steps[stepId].authorizedDocumentRoles[documentType][userRole];
        isProjectSpecific = steps[stepId].isProjectSpecificRole[userRole];
    }
    
    /**@dev This method is used to validate if a given role is required for a step and for a project
     * @param targetStep Identifier of the step where the authorized roles are set
     * @param userRole User role to validate
    */
    function roleAuthorization(bytes32 targetStep, bytes32 userRole) public view returns (bool isAuthorized, bool isProjectSpecific) {
        isAuthorized = steps[targetStep].roleInStep[userRole];
        isProjectSpecific = steps[targetStep].isProjectSpecificRole[userRole];
    }
    
    /**@dev This method is used to validate if certain step is the final step of a flow
     * @param stepId Identifier of the step to validate
    */
    function isFinalStep(bytes32 stepId) public view returns (bool) {
        return steps[stepId].isFinalStep;
    }
    
    /**@dev This method is used to validate if a role is required for a project to change step and upload a change for a document type
     * @param stepId Identifier of the step
     * @param userRole User role to validate
    */
    function isProjectSpecificRole(bytes32 stepId, bytes32 userRole) public view returns (bool) {
        return steps[stepId].isProjectSpecificRole[userRole];
    }
    
    /**@dev This method is used to validate if certain step can be used as an initial step for a project
     * @param stepId Identifier of the step to validate
    */
    function isInitialStep(bytes32 stepId) public view returns (bool) {
        return steps[stepId].isInitialStep;
    }
    
    /**@dev This method is used to get mandatory document types defined in a step
     * @param stepId Identifier of the step
    */
    function getMandatorydocumentKeysStep(bytes32 stepId) public view returns (bytes32[] memory) {
        return steps[stepId].mandatoryDocumentKeys;
    }
    
    /**@dev This method is used to get optional document types defined in a step
     * @param stepId Identifier of the step
    */
    function getOptionaldocumentKeysStep(bytes32 stepId) public view returns (bytes32[] memory) {
        return steps[stepId].optionalDocumentKeys;
    }
    
    /**@dev This method is used to validate if from one step you can move on to another step
     * @param currentStep Step identifier to be validated
     * @param targetStep Step identifier where it is verified if the current step is valid to make a step change
    */
    function isValidStepChange(bytes32 currentStep, bytes32 targetStep) public view returns (bool) {
        return steps[targetStep].previousValidSteps[currentStep];
    }
    
    /**@dev Verify that a document key is present in the step
     * @param stepId Step identifier
     * @param documentType Key to search in the step
    */
    function existsDocumentKeyInStep(bytes32 stepId, bytes32 documentType) public view returns (bool) {
        for (uint i = 0; i < steps[stepId].mandatoryDocumentKeys.length; i++) {
            if (steps[stepId].mandatoryDocumentKeys[i] == documentType) return true;
        }
        for (uint j = 0; j < steps[stepId].optionalDocumentKeys.length; j++) {
            if (steps[stepId].optionalDocumentKeys[j] == documentType) return true;
        }
        return false;
    }
}