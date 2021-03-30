package com.everis.blockchain.honduras.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import com.everis.blockchain.honduras.IDigitalIdentity;
import com.everis.blockchain.honduras.contract.IdentityManager;
import com.everis.blockchain.honduras.util.EthCore;
import com.everis.blockchain.honduras.util.EthCoreParams;
import com.everis.blockchain.honduras.util.Utils;

import io.reactivex.annotations.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
@Getter
@Setter
@Slf4j
public class DigitalIdentityImpl implements IDigitalIdentity {

	private EthCore ethCore;
	private String addressIM;
	private IdentityManager imContract;

	public DigitalIdentityImpl(@Nullable String identityManager, EthCoreParams ethCoreParams) throws IOException, Exception  {
		ethCore = new EthCore(ethCoreParams);
		
			addressIM = identityManager == null ? deployIM() : identityManager;
		
		updateIMContract();
	}

	// Deploy Contract
	public String deployIM() throws IOException, Exception {
		IdentityManager contract = IdentityManager
				.deploy(ethCore.getWeb3jInstance(), ethCore.getCredentials(), ethCore.getDefaultContractGasProvider())
				.send();
		addressIM = contract.getContractAddress();
		updateIMContract();
		return addressIM;
	}

	public String createIdentity(String keyMnemonic, String keyProfile, String urlProfile, String username, String salt)
			throws Exception {
		TransactionReceipt transactionReceipt = imContract.createIdentity(Utils.stringToBytes(keyMnemonic, 16),
				Utils.stringToBytes(keyProfile, 16), urlProfile, username, salt).send();
		List<Log> logs = transactionReceipt.getLogs();
		String proxy = null;
		for (Log logTx : logs) {
			// IdentityCreated
			if (logTx.getTopics().get(0).equals("0xac993fde3b9423ff59e4a23cded8e89074c9c8740920d1d870f586ba7c5c8cf0")) {
				proxy = "0x" + logTx.getData().substring(logTx.getData().length() - 40);
			}
		}
		if (proxy != null)
			return proxy;
		throw new Error("Could not create identity");
	}

	public String setCap(String identity, String device, String cap, BigInteger startDate, BigInteger endDate)
			throws Exception {
		TransactionReceipt transactionReceipt = imContract.setCap(identity, device, cap, startDate, endDate).send();
		return transactionReceipt.getTransactionHash();
	}

	@Override
	public Boolean hasCap(String identity, String device, String cap) {
		try {
			Boolean hasCap = imContract.hasCap(identity, device, cap).send();

			return hasCap;
		} catch (Exception e) {
			return false;
		}
	}

	public String forwardTo(String identity, String destination, BigInteger value, byte[] data) throws Exception {
		TransactionReceipt transactionReceipt = imContract.forwardTo(identity, destination, value, data).send();
		if (transactionReceipt.getStatus().equals("0x1"))
			return transactionReceipt.getTransactionHash();
		throw new Error("Could not forward transaction");
	}


	private void updateIMContract() {
		try {
			imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(),
					ethCore.getDefaultContractGasProvider());
		} catch (IOException e) {
			log.error("updateIMContract.Error", e);
		}
	}
	
	@Override
	public boolean existsProxyAddress(String address) {
		try {
			return imContract.registered_identities(address).send();
		} catch (Exception e) {
			return false;
		}
	}

}