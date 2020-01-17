package com.everis.blockchain.honduras;

import java.io.IOException;
import java.math.BigInteger;

public interface IDigitalIdentity {

    /**
     * This method is used to deploy a new Identity Manager contract.
     * @return This returns the contract address.
    */
    String deployIM() throws IOException, Exception;

    /**
     * This method is used to create a new identity.
     * @param keyMnemonic Key where the salt and username are stored in contract Id.
     * @param keyProfile Key where the public profile is stored in contract Id.
     * @param urlProfile Url where the user's public profile is stored.
     * @param username Username with which you can search for an identity through events.
     * @param salt It's used as part of the private key recovery text string.
     * Note: Username can be optional, if so the salt has no relevance.
     * @return This returns the contract address.
    */
    String createIdentity(String keyMnemonic, String keyProfile, String urlProfile, String username, String salt) throws Exception;

    /**
     * This method is used to check the capability of a device over an identity.
     * @param identity Proxy address of the user.
     * @param device Address to validate capability in IM.
     * @param cap Capability to validate.
     * @return Boolean
    */
    Boolean checkCap(String identity, String device, String cap) throws Exception;

    /**
     * This method is used to forward a transaction through IM using user's identity.
     * @param identity Proxy address of the user.
     * @param destination Address of the contract where the transaction will be execute.
     * @param value Value sent to a function if payable.
     * @param data Bytecode representation of the function to execute in the destination contract.
     * @return Transaction hash.
    */
    String forwardTo(String identity, String destination, BigInteger value, byte[] data) throws Exception;

    /**
     * This method is used to accredit a data through forward of an identity contract.
     * @param data Data to accredit.
     * @param validDays The number of days the data will be accredited.
     * @param identity Identity that will make the transaction.
     * @param AEO Contract address where certificate approval are made.
     * @param verificationRegistry Contract address where certificate accreditation/revocation are made.
     * @return Transaction hash.
    */
    String approveAEO(String data, BigInteger validDays, String identity, String AEO, String verificationRegistry) throws Exception;

    /**
     * This method is used to revoke a data through forward of an identity contract.
     * @param data Data to revoke.
     * @param identity Identity that will make the transaction.
     * @param AEO Contract address where certificate approval are made.
     * @param verificationRegistry Contract address where certificate accreditation/revocation are made.
     * @return Transaction hash.
    */
    String revokeAEO(String data, String identity, String AEO, String verificationRegistry) throws Exception;

    /**
     * This method is used to register a AEO.
     * @param data Data to register.
     * @param identity Identity that will make the transaction.
     * @param AEO Contract address where certificate registry are made (AEOManager).
     * @return Transaction hash.
    */
    String registerAEO(String data, String identity, String AEO) throws Exception;

    /**
     * This method is used to add a role to user in contract AEOManager.
     * @param identity Identity that will make the transaction.
     * @param AEO Contract address of AEOManager.
     * @param user Address of the user to add a role.
     * @param role Role to add.
     * @return Transaction hash.
    */
    String addRoleUserAEOManager(String identity, String AEO, String user, String role) throws Exception;

    /**
     * This method is used to remove a role to user in contract AEOManager.
     * @param identity Identity that will make the transaction.
     * @param AEO Contract address of AEOManager.
     * @param user Address of the user to remove a role.
     * @param role Role to remove.
     * @return Transaction hash.
    */
    String removeRoleUserAEOManager(String identity, String AEO, String user, String role) throws Exception;
    
}