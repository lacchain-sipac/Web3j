package com.everis.blockchain.honduras.main.model;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.IStepManager;
import com.everis.blockchain.honduras.impl.StepManagerImpl;
import com.everis.blockchain.honduras.util.EthCoreParams;

public class GenerateStepManager {

	private static final Logger log = LoggerFactory.getLogger("GenerateStepManager");
	private EthCoreParams ethCoreParams;
	private LinkedHashMap<String, String> map;

	public GenerateStepManager(LinkedHashMap<String, String> map,  Map<String, String> headers) {
		this.map = map;
		this.ethCoreParams = new EthCoreParams(map.get(Constants.BLOCKCHAIN_SERVER),
				map.get(Constants.PRIVATE_KEY_BACKEND), null, BigInteger.valueOf(0), null);

		ethCoreParams.setHeaders(headers);
	}

	public void create() throws Exception {
		if (StringUtils.isEmpty(map.get(Constants.CONTRACT_ADDRESS_STEP_MANAGER))) {
			deploy();
		}
	}

	private void deploy() throws Exception {

		IStepManager stemT = new StepManagerImpl(Constants.DUMMY_STEP, ethCoreParams);

		String contractAddress = stemT.deployStepManager();

		map.put(Constants.CONTRACT_ADDRESS_STEP_MANAGER, contractAddress);

		log.info("CONTRACT_ADDRESS_STEP_MANAGER : " + map.get(Constants.CONTRACT_ADDRESS_STEP_MANAGER));

	}

}
