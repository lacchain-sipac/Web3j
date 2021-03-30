package com.everis.blockchain.honduras;

import io.reactivex.annotations.Nullable;

public interface IOwner {

    /**
     * This method is used to add a new address as contract owner
     * @param addressContract Address of the contract
     * @param newOwner Address to add as new owner of the contract
     * @param isThroughIM Specifies whether the transaction is direct or through the identity of a user
     * @param addressUser If "isThroughIM" is true with this parameter, the identity of the user who will carry out the transaction is specified
     * @param addressIM If "isThroughIM" is true, this parameter is used to indicate the identity manager contract to use
     * @return Transaction hash
    */
    String addOwner(String addressContract, String newOwner, Boolean isThroughIM, @Nullable String addressUser, @Nullable String addressIM) throws Exception;

    /**
     * This method is used to renounce ownership of a contract
     * @param addressContract Address of the contract
     * @param isThroughIM Specifies whether the transaction is direct or through the identity of a user
     * @param addressUser If "isThroughIM" is true with this parameter, the identity of the user who will carry out the transaction is specified
     * @param addressIM If "isThroughIM" is true, this parameter is used to indicate the identity manager contract to use
     * @return Transaction hash
    */
    String renounce(String addressContract, Boolean isThroughIM, @Nullable String addressUser, @Nullable String addressIM) throws Exception;
    
    /**
     * This method is used to validate if given address is owner of the contract
     * @param addressContract Address of the contract
     * @param addressUser Address of the user to validate if is owner in contract
     * @return Boolean
    */
    Boolean isOwner(String addressContract, String addressUser) throws Exception;
    
}