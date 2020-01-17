package com.everis.blockchain.honduras.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.everis.blockchain.honduras.http.EthCore;
import com.everis.blockchain.honduras.http.EthCoreParams;
import com.everis.blockchain.honduras.util.Utils;

import io.reactivex.annotations.Nullable;

public class DigitalIdentityImpl implements IDigitalIdentity {

	private EthCore ethCore;
	public String addressIM;
	private IdentityManager imContract;
	private static final Logger log = LoggerFactory.getLogger(DigitalIdentityImpl.class);

	public DigitalIdentityImpl(@Nullable String identityManager, EthCoreParams ethCoreParams, boolean isSSL) {
		ethCore = new EthCore(ethCoreParams, isSSL);
		try {
			addressIM = identityManager == null ? deployIM() : identityManager;
		} catch (Exception e) {
			addressIM = "0x0000000000000000000000000000000000000000";
			log.error("Error.DigitalIdentityImpl", e);
		}
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

	public Boolean checkCap(String identity, String device, String cap) throws Exception {
		Boolean hasCap = imContract.hasCap(identity, device, cap).send();
		return hasCap;
	}

	public String forwardTo(String identity, String destination, BigInteger value, byte[] data) throws Exception {
		TransactionReceipt transactionReceipt = imContract.forwardTo(identity, destination, value, data).send();
		if (transactionReceipt.getStatus().equals("0x1"))
			return transactionReceipt.getTransactionHash();
		throw new Error("Could not forward transaction");
	}

	public String addRoleUserAEOManager(String identity, String AEO, String user, String role) throws Exception {
		Function functionAddRole = new Function("addRole", Arrays.asList(new Address(160, user), new Utf8String(role)),
				Collections.<TypeReference<?>>emptyList());
		String encodedFunction = FunctionEncoder.encode(functionAddRole);
		byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
		String txHash = forwardTo(identity, AEO, BigInteger.valueOf(0), dataBytes);
		return txHash;
	}

	public String removeRoleUserAEOManager(String identity, String AEO, String user, String role) throws Exception {
		Function functionRemoveRole = new Function("removeRole",
				Arrays.asList(new Address(160, user), new Utf8String(role)), Collections.<TypeReference<?>>emptyList());
		String encodedFunction = FunctionEncoder.encode(functionRemoveRole);
		byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
		String txHash = forwardTo(identity, AEO, BigInteger.valueOf(0), dataBytes);
		return txHash;
	}

	public String approveAEO(String data, BigInteger validDays, String identity, String AEO,
			String verificationRegistry) throws Exception {
		byte[] hash = Utils.calculateHash(data);
		Function functionApprove = new Function("approveAEO", Arrays.asList(new Bytes32(hash), new Uint256(validDays),
				new Address(160, addressIM), new Address(160, verificationRegistry)),
				Collections.<TypeReference<?>>emptyList());
		String encodedFunction = FunctionEncoder.encode(functionApprove);
		byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
		String txHash = forwardTo(identity, AEO, BigInteger.valueOf(0), dataBytes);
		return txHash;
	}

	public String revokeAEO(String data, String identity, String AEO, String verificationRegistry) throws Exception {
		byte[] hash = Utils.calculateHash(data);
		Function functionRevoke = new Function("revokeAEO",
				Arrays.asList(new Bytes32(hash), new Address(160, addressIM), new Address(160, verificationRegistry)),
				Collections.<TypeReference<?>>emptyList());
		String encodedFunction = FunctionEncoder.encode(functionRevoke);
		byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
		String txHash = forwardTo(identity, AEO, BigInteger.valueOf(0), dataBytes);
		return txHash;
	}

	public String registerAEO(String data, String identity, String AEO) throws Exception {
		byte[] hash = Utils.calculateHash(data);
		Function functionRegister = new Function("registerAEO", Arrays.asList(new Bytes32(hash)),
				Collections.<TypeReference<?>>emptyList());
		String encodedFunction = FunctionEncoder.encode(functionRegister);
		byte[] dataBytes = Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
		String txHash = forwardTo(identity, AEO, BigInteger.valueOf(0), dataBytes);
		return txHash;
	}

	private void updateIMContract() {
		try {
			imContract = IdentityManager.load(addressIM, ethCore.getWeb3jInstance(), ethCore.getCredentials(),
					ethCore.getDefaultContractGasProvider());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}