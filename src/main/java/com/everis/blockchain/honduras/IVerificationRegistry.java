package com.everis.blockchain.honduras;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public interface IVerificationRegistry {

    /**
     * This method is used to deploy a new Verification Registry contract.
     * @return This returns the contract address.
    */
    String deployVR() throws IOException, Exception;

    /**
     * This method is used to accredit a data.
     * @param data Data to accredit.
     * @param validDays The number of days the data will be accredited.
     * @return Transaction hash.
    */
    String verify(String data, BigInteger validDays) throws Exception;

    /**
     * This method is used to revoke a data.
     * @param data Data to revoke.
     * @return Transaction hash.
    */
    String revoke(String data) throws Exception;

    /**
     * This method is used to get validity dates of certain hash.
     * @param data Data to get dates.
     * @param identity Identity that makes the accreditation.
     * @return Array of timestamp with start and expiration dates.
    */
    List<BigInteger> getDatesVerification(String data, String identity) throws Exception;
}