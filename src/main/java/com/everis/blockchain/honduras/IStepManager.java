package com.everis.blockchain.honduras;

import java.math.BigInteger;

public interface IStepManager {

    /**
     * This method is used to deploy a new Step Manager contract
     * @return This returns the contract address
    */
    String deployStepManager() throws Exception;

    /**
     * This method is used to initialize a project in the contract using a identifier for the project
     * @param projectCodeHash Project Identifier
     * @param firstStep Initial step with which the project begins. Step is declared in the Flow contract
     * @param flowContract Contract where the possible steps for a process are defined
     * @param rolesContract Address of the contract with the definition of roles by user
     * @param userRole Role valid to init a new project. It must define as authorized role in the step
     * @return Transaction hash
    */
    String initProject(String projectCodeHash, String firstStep, String flowContract, String rolesContract, String userRole) throws Exception;

    /**
     * This method is used to initialize a project in the contract using a identifier for the project
     * This mehtod use the identity manager to make the transaction
     * @param projectCodeHash Project Identifier
     * @param firstStep Initial step with which the project begins. Step is declared in the Flow contract
     * @param flowContract Contract where the possible steps for a process are defined
     * @param rolesContract Address of the contract with the definition of roles by user
     * @param userRole Role valid to init a new project. It must define as authorized role in the step
     * @param addressUser Identity that will make the transaction
     * @param addressIM Identity manager contract to use
     * @return Transaction hash
    */
    String initProjectIM(String projectCodeHash, String firstStep, String flowContract, String rolesContract, String userRole, String addressUser, String addressIM) throws Exception;

    /**
     * This method is used to change the current status of a project to a new step previously defined in the Flow contract
     * @param projectCodeHash Project Identifier
     * @param targetStep Step to which the project will be passed
     * @param userRole Role of the user who will perform the action
     * @return Transaction hash
    */
    String changeStep(String projectCodeHash, String targetStep, String userRole) throws Exception;
    
    /**
     * This method is used to change the current status of a project to a new step previously defined in the Flow contract
     * This mehtod use the identity manager to make the transaction
     * @param projectCodeHash Project Identifier
     * @param targetStep Step to which the project will be passed
     * @param userRole Role of the user who will perform the action
     * @param addressUser Identity that will make the transaction
     * @param addressIM Identity manager contract to use
     * @return Transaction hash
    */
    String changeStepIM(String projectCodeHash, String targetStep, String userRole, String addressUser, String addressIM) throws Exception;

    /**
     * This method is used to upload a list of changes for a document type
     * @param projectCodeHash Project Identifier
     * @param documentType Type of document that will contain the change information
     * @param stepId Step where the change for a document type will be store
     * @param userRole Role of the user who will perform the action
     * @param accreditDocumentHash Hash of the document to accredit
     * @param revokeDocumentHash Hash of the document to revoke
     * @param accreditCommentHash Hash of the comment to accredit
     * @param isFinal Close a type of document and do not allow new data entry
     * @return Transaction hash
    */
    String setDocumentType(String projectCodeHash, String documentType, String stepId, String userRole, String accreditDocumentHash, String revokeDocumentHash, String accreditCommentHash, Boolean isFinal) throws Exception;
    
    /**
     * This method is used to upload a list of changes for a document type
     * This mehtod use the identity manager to make the transaction
     * @param projectCodeHash Project Identifier
     * @param documentType Type of document that will contain the change information
     * @param stepId Step where the change for a document type will be store
     * @param userRole Role of the user who will perform the action
     * @param accreditDocumentHash Hash of the document to accredit
     * @param revokeDocumentHash Hash of the document to revoke
     * @param accreditCommentHash Hash of the comment to accredit
     * @param isFinal Close a type of document and do not allow new data entry
     * @param addressUser Identity that will make the transaction
     * @param addressIM Identity manager contract to use
     * @return Transaction hash
    */
    String setDocumentTypeIM(String projectCodeHash, String documentType, String stepId, String userRole, String accreditDocumentHash, String revokeDocumentHash, String accreditCommentHash, Boolean isFinal, String addressUser, String addressIM) throws Exception;

    /**
     * This method is used to allow new data entry to a type of document
     * @param projectCodeHash Project Identifier
     * @param documentType Key to identify the document type
     * @param userRole Role of the user who will perform the action
     * @return Transaction hash
    */
    String unfinalizeDocumentType(String projectCodeHash, String documentType, String userRole) throws Exception;
    
    /**
     * This method is used to allow new data entry to a type of document
     * This mehtod use the identity manager to make the transaction
     * @param projectCodeHash Project Identifier
     * @param documentType Key to identify the document type
     * @param userRole Role of the user who will perform the action
     * @param addressUser Identity that will make the transaction
     * @param addressIM Identity manager contract to use
     * @return Transaction hash
    */
    String unfinalizeDocumentTypeIM(String projectCodeHash, String documentType, String userRole, String addressUser, String addressIM) throws Exception;

    /**
     * This method is used to get the total of changes store for a specific document type
     * @param projectCodeHash Project Identifier
     * @param documentType Key to identify the document type
     * @return Total of changes
    */
    BigInteger getDocumentTypeCount(String projectCodeHash, String documentType) throws Exception;

    /**
     * This method is used to get the information for a specific change
     * @param projectCodeHash Project Identifier
     * @param documentType Key to identify the document type
     * @param index Index where the change is store in the array
     * @return Change
    */
    Change getDocumentType(String projectCodeHash, String documentType, BigInteger index) throws Exception;

    /**
     * Check if a comment has been credited for a document type
     * @param projectCode Project Identifier
     * @param documentType Key of de document type
     * @param hashComment Comment hash to verify
     * @return Change
    */
    Change getDocumentTypeByComment(String projectCodeHash, String documentType, String commentHash) throws Exception;

    /**
     * Check if a document has been credited for a document type
     * @param projectCode Project Identifier
     * @param documentType Key of de document type
     * @param hashDocument Document hash to verify
     * @return Change
    */
    Change getDocumentTypeByDocument(String projectCodeHash, String documentType, String hashDocument) throws Exception;

    /**
     * This method is used to validate if a project has gone through a given stage
     * @param projectCodeHash Project Identifier
     * @param stepId Step to validate
     * @return Boolean
    */
    Boolean isStepCompletedProject(String projectCodeHash, String stepId) throws Exception;
    
    /**
     * This method is used to verify that all required documents are present in the current step for a project
     * @param projectCodeHash Project Identifier
     * @return Boolean
    */
    Boolean hasValidDocuments(String projectCodeHash) throws Exception;

    /**
     * This method is used to get the information for a project
     * @param projectCodeHash Project Identifier
     * @return Project
    */
    Project getProject(String projectCodeHash) throws Exception;
    
}