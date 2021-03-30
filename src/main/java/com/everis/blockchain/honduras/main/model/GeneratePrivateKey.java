package com.everis.blockchain.honduras.main.model;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.util.Account;
import com.everis.blockchain.honduras.util.EthCore;
import com.everis.blockchain.honduras.util.EthCoreParams;

public class GeneratePrivateKey {

	private static final Logger log = LoggerFactory.getLogger("GeneratePrivateKey");
	private LinkedHashMap<String, String> map;
	private EthCore ethCore;

	public GeneratePrivateKey(LinkedHashMap<String, String> map, Map<String, String> headers) {
		this.map = map;
		EthCoreParams ethCoreParams = new EthCoreParams(map.get(Constants.BLOCKCHAIN_SERVER), Constants.DUMMY_PK, null,
				BigInteger.valueOf(0), BigInteger.valueOf(4000000));
		ethCoreParams.setHeaders(headers);
		
		ethCore = new EthCore(ethCoreParams);
	}

	public void create() throws Exception {

		if (StringUtils.isEmpty(map.get(Constants.PRIVATE_KEY_BACKEND))
				|| StringUtils.isEmpty(map.get(Constants.ADDRESS_ETHEREUM_BACKEND))) {
			
			Account account = ethCore.createCredentials("");

			map.put(Constants.PRIVATE_KEY_BACKEND, account.getPrivateKey());

			map.put(Constants.ADDRESS_ETHEREUM_BACKEND, account.getAddress());

			log.info("PRIVATE_KEY_BACKEND : " + map.get(Constants.PRIVATE_KEY_BACKEND));
			log.info("ADDRESS_ETHEREUM_BACKEND : " + map.get(Constants.ADDRESS_ETHEREUM_BACKEND));
		}

	}
}
