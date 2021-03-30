package com.everis.blockchain.honduras.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.ChainId;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.exceptions.TxHashMismatchException;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;
import org.web3j.utils.TxHashVerifier;

public class RawTransactionManagerSimple extends TransactionManager {
	private static final Logger log = LoggerFactory.getLogger(RawTransactionManagerSimple.class);
	private static final BigDecimal ADITIONAL_GAS = new BigDecimal("1.5");

	private final Web3 web3j;
	final Credentials credentials;

	private final long chainId;

	protected TxHashVerifier txHashVerifier = new TxHashVerifier();

	public RawTransactionManagerSimple(Web3 web3j, Credentials credentials, long chainId) {
		super(web3j, credentials.getAddress());

		this.web3j = web3j;
		this.credentials = credentials;

		this.chainId = chainId;
	}

	public RawTransactionManagerSimple(Web3 web3j, Credentials credentials, long chainId,
			TransactionReceiptProcessor transactionReceiptProcessor) {
		super(transactionReceiptProcessor, credentials.getAddress());

		this.web3j = web3j;
		this.credentials = credentials;

		this.chainId = chainId;
	}

	public RawTransactionManagerSimple(Web3 web3j, Credentials credentials, long chainId, int attempts,
			long sleepDuration) {
		super(web3j, attempts, sleepDuration, credentials.getAddress());

		this.web3j = web3j;
		this.credentials = credentials;

		this.chainId = chainId;
	}

	public RawTransactionManagerSimple(Web3 web3j, Credentials credentials) {
		this(web3j, credentials, ChainId.NONE);
	}

	public RawTransactionManagerSimple(Web3 web3j, Credentials credentials, int attempts, int sleepDuration) {
		this(web3j, credentials, ChainId.NONE, attempts, sleepDuration);
	}

	protected BigInteger getNonce() throws IOException {
		EthGetTransactionCount ethGetTransactionCount = web3j
				.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();

		return ethGetTransactionCount.getTransactionCount();
	}

	public TxHashVerifier getTxHashVerifier() {
		return txHashVerifier;
	}

	public void setTxHashVerifier(TxHashVerifier txHashVerifier) {
		this.txHashVerifier = txHashVerifier;
	}

	@Override
	public EthSendTransaction sendTransaction(BigInteger gasPrice, BigInteger gasLimit, String to, String data,
			BigInteger value, boolean constructor) throws IOException {

		BigInteger nonce = getNonce();

		if (gasLimit == null) {
			gasLimit = getEstimateGasLimit(to, data);
		}
		log.info("gasLimit ==> {}, nonce ==>{}", gasLimit, nonce);

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);

		return signAndSend(rawTransaction);
	}

	protected BigInteger getEstimateGasLimit(String to, String data) throws IOException {
		EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(credentials.getAddress(), to, data).send();
		return ADITIONAL_GAS.multiply(new BigDecimal(ethEstimateGas.getAmountUsed())).toBigInteger();
	}

	@Override
	public String sendCall(String to, String data, DefaultBlockParameter defaultBlockParameter) throws IOException {
		EthCall ethCall = web3j
				.ethCall(Transaction.createEthCallTransaction(getFromAddress(), to, data), defaultBlockParameter)
				.send();

		assertCallNotReverted(ethCall);
		return ethCall.getValue();
	}

	static void assertCallNotReverted(EthCall ethCall) {
		if (ethCall.isReverted()) {
			throw new ContractCallException(String.format(REVERT_ERR_STR, ethCall.getRevertReason()));
		}
	}

	@Override
	public EthGetCode getCode(final String contractAddress, final DefaultBlockParameter defaultBlockParameter)
			throws IOException {
		return web3j.ethGetCode(contractAddress, defaultBlockParameter).send();
	}

	/*
	 * @param rawTransaction a RawTransaction istance to be signed
	 * 
	 * @return The transaction signed and encoded without ever broadcasting it
	 */
	public String sign(RawTransaction rawTransaction) {

		byte[] signedMessage;

		if (chainId > ChainId.NONE) {
			signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
		} else {
			signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		}

		return Numeric.toHexString(signedMessage);
	}

	public EthSendTransaction signAndSend(RawTransaction rawTransaction) throws IOException {
		String hexValue = sign(rawTransaction);
		EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

		if (ethSendTransaction != null && !ethSendTransaction.hasError()) {
			String txHashLocal = Hash.sha3(hexValue);
			String txHashRemote = ethSendTransaction.getTransactionHash();
			if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
				throw new TxHashMismatchException(txHashLocal, txHashRemote);
			}
		}

		return ethSendTransaction;
	}

}
