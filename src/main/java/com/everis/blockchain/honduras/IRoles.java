package com.everis.blockchain.honduras;

public interface IRoles {

    /**
     * This method is used to deploy a new Roles contract
     * @param ownerRole Role used to add roles to users globally throughout the project
     * @param projectOwnerRole Role used to add roles to users specifically for a project
     * @return This returns the contract address
    */
    String deployRoles(String ownerRole, String projectOwnerRole) throws Exception;

    /**
     * This method is used to assign a specific role to a user
     * @param role Role to set
     * @param user Address of the user to set the role
     * @return Transaction hash
    */
    String setRoleUser(String role, String user) throws Exception;
    
    /**
     * This method is used to assign a specific role to a user for a project
     * @param role Role to set
     * @param user Address of the user to set the role
     * @param projectCodeHash Project code where the role is to be assigned
     * @return Transaction hash
    */
    String setRoleUser(String role, String user, String projectCodeHash) throws Exception;
    
    /**
     * This method is used to assign a role to a user using the identity of a user of the system as the origin of the transaction
     * @param role Role to set
     * @param user Address of the user to set the role
     * @param addressUser Identity that will make the transaction
     * @param addressIM Identity manager contract to use
     * @return Transaction hash
    */
    String setRoleUserIM(String role, String user, String addressUser, String addressIM) throws Exception;
    
    /**
     * This method is used to assign a role to a user for a project using the identity of a user of the system as the origin of the transaction
     * @param role Role to set
     * @param user Address of the user to set the role
     * @param projectCodeHash Project code where the role is to be assigned
     * @param addressUser Identity that will make the transaction
     * @param addressIM Identity manager contract to use
     * @return Transaction hash
    */
    String setRoleUserIM(String role, String user, String projectCodeHash, String addressUser, String addressIM) throws Exception;

    /**
     * This method is used to remove a specific role to user
     * @param role Role to remove
     * @param user Address of the user to remove the role
     * @return Transaction hash
    */
    String removeRoleUser(String role, String user) throws Exception;

    /**
     * This method is used to remove a specific role to user for a project
     * @param role Role to remove
     * @param user Address of the user to remove the role
     * @param projectCodeHash Project code where the role is to be removed
     * @return Transaction hash
    */
    String removeRoleUser(String role, String user, String projectCodeHash) throws Exception;
    
    /**
     * This method is used to remove a specific role to user through the identity of a user of the system as the origin of the transaction
     * @param role Role to remove
     * @param user Address of the user to remove the role
     * @param addressUser Identity that will make the transaction
     * @param addressIM Identity manager contract to use
     * @return Transaction hash
    */
    String removeRoleUserIM(String role, String user, String addressUser, String addressIM) throws Exception;

    /**
     * This method is used to remove a specific role to user for a project through the identity of a user of the system as the origin of the transaction
     * @param role Role to remove
     * @param user Address of the user to remove the role
     * @param projectCodeHash Project code where the role is to be removed
     * @param addressUser Identity that will make the transaction
     * @param addressIM Identity manager contract to use
     * @return Transaction hash
    */
    String removeRoleUserIM(String role, String user, String projectCodeHash, String addressUser, String addressIM) throws Exception;

    /**
     * This method is used to verify if certain user has a role
     * @param role Role to verify
     * @param user Address of the user to verify the role
     * @return Boolean
    */
    Boolean hasRoleUser(String role, String user) throws Exception;
    
    /**
     * This method is used to verify if certain user has a role
     * @param role Role to verify
     * @param user Address of the user to verify the role
     * @param projectCodeHash Project code used to check if a user has a specific role
     * @return Boolean
    */
    Boolean hasRoleUser(String role, String user, String projectCodeHash) throws Exception;
    
}