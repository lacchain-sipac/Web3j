package com.everis.blockchain.honduras.util;

import java.util.Arrays;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthEstimateGas;

public class Web3Impl extends JsonRpc2_0Web3j implements Web3 {

	public Web3Impl(Web3jService web3jService) {
		super(web3jService);
	}

	public Request<?, EthEstimateGas> ethEstimateGas(String from, String to, String data) {
		return new Request<>("eth_estimateGas", Arrays.asList(new EstimateGas(from, to, data)), web3jService, EthEstimateGas.class);
	}
	

}
