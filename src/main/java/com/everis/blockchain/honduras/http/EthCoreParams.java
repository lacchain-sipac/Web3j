package com.everis.blockchain.honduras.http;

import java.math.BigInteger;

import io.reactivex.annotations.Nullable;

public class EthCoreParams {

	String host;
	String privateKey;
	String seed;
	BigInteger gasPrice;
	BigInteger gasLimit;

	public EthCoreParams(String hostParam, @Nullable String privatekey, @Nullable String seedOrigin, @Nullable BigInteger gPrice, @Nullable BigInteger gLimit) {
		host = hostParam;
		privateKey = privatekey;
		seed = seedOrigin;
		gasPrice = gPrice;
		gasLimit = gLimit;
	}

}