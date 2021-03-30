package com.everis.blockchain.honduras.util;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthEstimateGas;

public interface Web3 extends Web3j {

	
	   /**
     * Construct a new Web3j instance.
     *
     * @param web3jService web3j service instance - i.e. HTTP or IPC
     * @return new Web3j instance
     */
    static Web3 build(Web3jService web3jService) {
        return new Web3Impl(web3jService);
    }
	
    Request<?, EthEstimateGas> ethEstimateGas(String from, String to , String data);
}
