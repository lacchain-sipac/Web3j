package com.everis.blockchain.honduras.util;

import java.math.BigInteger;
import java.util.Map;

import io.reactivex.annotations.Nullable;
import lombok.Getter;
import lombok.Setter;

public class EthCoreParams {

	private String host;
	private String privateKey;
	private String seed;
	private BigInteger gasPrice;
	private BigInteger gasLimit;
	@Setter
	@Getter
	private Map<String, String> headers;


	public EthCoreParams(String hostParam, @Nullable String privatekey, @Nullable String seedOrigin,
			@Nullable BigInteger gPrice, @Nullable BigInteger gLimit) {
		host = hostParam;
		privateKey = privatekey;
		seed = seedOrigin;
		gasPrice = gPrice;
		gasLimit = gLimit;
	}

	public String getHost() {
		return host;
	}

	public String getSeed() {
		return seed;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public BigInteger getGasPrice() {
		return gasPrice;
	}

	public BigInteger getGasLimit() {
		return gasLimit;
	}

}