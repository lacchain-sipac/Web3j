pragma solidity ^0.5.16;

contract Roles {
    
    // Address role users => role = bool
    mapping (address => mapping (bytes32 => bool)) private userRoles;
    // Project code => Address role users => role = bool
    mapping (bytes32 => mapping (address => mapping (bytes32 => bool))) private userRolesByProject;
    bytes32 public OWNER_ROLE;
    bytes32 public PROJECT_OWNER_ROLE;
    
    modifier onlyRole(bytes32 role) {
        require(userRoles[msg.sender][role], "user does not have sufficient privileges");
        _;
    }
    
    /**@dev Constructor
     * @param ownerRole Role used to add roles to users globally throughout the project
     * @param projectOwnerRole Role used to add roles to users specifically for a project
    */
    constructor(bytes32 ownerRole, bytes32 projectOwnerRole) public {
        OWNER_ROLE = ownerRole;
        PROJECT_OWNER_ROLE = projectOwnerRole;
        userRoles[msg.sender][ownerRole] = true;
    }
    
    /**@dev This method is used to assign a specific role to a user
     * @param role Role to set
     * @param user Address of the user to set the role
    */
    function setRoleUser(bytes32 role, address user) public onlyRole(OWNER_ROLE) {
        require(!userRoles[user][role], "role was already set");
        userRoles[user][role] = true;
    }
    
    /**@dev This method is used to assign a specific role to a user for a project
     * @param role Role to set
     * @param user Address of the user to set the role
     * @param projectCode Project code where the role is to be assigned
    */
    function setRoleUser(bytes32 role, address user, bytes32 projectCode) public onlyRole(PROJECT_OWNER_ROLE) {
        require(!userRolesByProject[projectCode][user][role], "role was already set");
        userRolesByProject[projectCode][user][role] = true;
    }
    
    /**@dev This method is used to remove a specific role to user
     * @param role Role to remove
     * @param user Address of the user to remove the role
    */
    function removeRoleUser(bytes32 role, address user) public onlyRole(OWNER_ROLE) {
        require(userRoles[user][role], "no role set");
        userRoles[user][role] = false;
    }
    
    /**@dev This method is used to remove a specific role to user for a project
     * @param role Role to remove
     * @param user Address of the user to remove the role
     * @param projectCode Project code where the role is to be removed
    */
    function removeRoleUser(bytes32 role, address user, bytes32 projectCode) public onlyRole(PROJECT_OWNER_ROLE) {
        require(userRolesByProject[projectCode][user][role], "no role set");
        userRolesByProject[projectCode][user][role] = false;
    }
    
    /**@dev This method is used to verify if certain user has a role
     * @param role Role to verify
     * @param user Address of the user to verify the role
    */
    function hasRoleUser(bytes32 role, address user) public view returns (bool) {
        return userRoles[user][role];
    }
    
    /**@dev This method is used to verify if certain user has a role
     * @param role Role to verify
     * @param user Address of the user to verify the role
     * @param projectCode Project code used to check if a user has a specific role
    */
    function hasRoleUser(bytes32 role, address user, bytes32 projectCode) public view returns (bool) {
        return userRolesByProject[projectCode][user][role];
    }
    
}