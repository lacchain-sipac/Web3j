package com.everis.blockchain.honduras;

import java.util.ArrayList;

import com.everis.blockchain.honduras.impl.Authorization;

public interface IFlow {

    /**
     * This method is used to deploy a new Flow contract
     * @return This returns the contract address
    */
    String deployFlow() throws Exception;

    /**
     * This method is used to add a new step for a flow defining necessary and optional roles and documents
     * @param stepId Identifier of the step to add
     * @param mandatoryDocumentKeys Types of documents required for this step. Keys are used to identify each
     * @param optionalDocumentKeys Types of documents optional for this step
     * @param authorizedRoles Types of roles authorized to reach this step
     * @param previousValidSteps Allowed steps that can go to this step
     * @param projectSpecificRoles Specific roles for a project to set add a new change for a document type and to reach this step
     * @param isFinalStep Define if a step represents the end of a flow
     * @param isInitialStep Define if a step its possible to use as a first step in the initialization of a project
     * @return Transaction hash
    */
    String addStep(String stepId, String[] mandatoryDocumentKeys, String[] optionalDocumentKeys, String[] authorizedRoles, String[] previousValidSteps , String[] projectSpecificRoles, Boolean isFinalStep, Boolean isInitialStep) throws Exception;

    /**
     * This method is used to authorize or not a list of user roles to reach this stage. Roles used to validate which role can change to this step
     * @param stepId Identifier of the step where the roles are going to be set
     * @param roles List of roles
     * @param status Status to be established for each role
     * @return Transaction hash
    */
    String setStatusAuthorizedRolesStep(String stepId, String[] roles, Boolean status) throws Exception;

    /**
     * This method is used to establish which are the valid steps to reach or not the current step
     * @param stepId Identifier of the step where the previous steps are going to be set
     * @param previousValidSteps Steps that can go or not to this step
     * @param status Status to be established
     * @return Transaction hash
    */
    String setPreviousValidStep(String stepId, String[] previousValidSteps, Boolean status) throws Exception;
    
    /**
     * This method is used to establish which roles can set or not a hash for a document type
     * @param stepId Identifier of the step where permissions are going to be set
     * @param documentType Key that identifies the document type
     * @param userRoles List of users roles to authorized
     * @param status Status to be established
     * @return Transaction hash
    */
    String setStatusAuthorizedDocumentRole(String stepId, String documentType, String[] userRoles, Boolean status) throws Exception;
    
    /**
     * This method is used to define roles that are specific to a project for the step change or load change by document type
     * @param stepId Identifier of the step where permissions are going to be set
     * @param projectSpecificRoles List of roles to be established
     * @param status Status to be established
     * @return Transaction hash
    */
    String setProjectSpecificRoles(String stepId, String[] projectSpecificRoles, Boolean status) throws Exception;
    
    /**
     * This method is used to validate if a given role is required to upload a change for a document type at the stage and project level
     * @param stepId Identifier of the step where the document type is present
     * @param documentType Key that identifies the document type
     * @param userRole User role
     * @return Authorization
    */
    Authorization documentRoleAuthorization(String stepId, String documentType, String userRole) throws Exception;
    
    /**
     * This method is used to validate if a given role is required for a step and for a project
     * @param targetStep Identifier of the step where the authorized roles are set
     * @param userRole User role to validate
     * @return Authorization
    */
    Authorization roleAuthorization(String targetStep, String userRole) throws Exception;
    
    /**
     * This method is used to validate if certain step is the final step of a flow
     * @param stepId Identifier of the step to validate
     * @return Boolean
    */
    Boolean isFinalStep(String stepId) throws Exception;
    
    /**
     * This method is used to validate if a role is required for a project to change step and upload a change for a document type
     * @param stepId Identifier of the step
     * @param userRole User role to validate
     * @return Boolean
    */
    Boolean isProjectSpecificRole(String stepId, String userRole) throws Exception;
    
    /**
     * This method is used to validate if certain step can be used as an initial step for a project
     * @param stepId Identifier of the step to validate
     * @return Boolean
    */
    Boolean isInitialStep(String stepId) throws Exception;
    
    /**
     * This method is used to get mandatory document types defined in a step
     * @param stepId Identifier of the step
     * @return ArrayList<String>
    */
    ArrayList<String> getMandatorydocumentKeysStep(String stepId) throws Exception;
    
    /**
     * This method is used to get optional document types defined in a step
     * @param stepId Identifier of the step
     * @return ArrayList<String>
    */
    ArrayList<String> getOptionaldocumentKeysStep(String stepId) throws Exception;
    
    /**
     * This method is used to validate if from one step you can move on to another step
     * @param currentStep Step identifier to be validated
     * @param targetStep Step identifier where it is verified if the current step is valid to make a step change
     * @return Boolean
    */
    Boolean isValidStepChange(String currentStep, String targetStep) throws Exception;
    
    /**
     * This method is used to verify that a document key is present in the step
     * @param stepId Step identifier
     * @param documentType Key to search in the step
     * @return Boolean
    */
    Boolean existsDocumentKeyInStep(String stepId, String documentType) throws Exception;
    
}