package com.everis.blockchain.honduras.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import io.reactivex.annotations.Nullable;
import okhttp3.OkHttpClient;

public class EthCore {

	private static final Logger log = LoggerFactory.getLogger(EthCore.class);
	private Web3 web3;
	private Credentials credentials;
	private String networkId;
	private BigInteger gasPrice;
	private BigInteger gasLimit;
	

	public EthCore(EthCoreParams params) {
		
		HttpService httpService =	new HttpService(params.getHost(), createOkHttpClient(params.getHost().contains("https:")), true);
		
		if (params.getHeaders() != null && !params.getHeaders().isEmpty())
			httpService.addHeaders(params.getHeaders());
	
		web3 = Web3.build(httpService);
		
		initVars(params);
	}

	private void initVars(EthCoreParams params) {
		try {
			String privateKey = generatePrivateKey(params);
			credentials = Credentials.create(privateKey);
			networkId = web3.netVersion().send().getResult();
			gasPrice = params.getGasPrice() != null ? params.getGasPrice() : getGasPrice();
			gasLimit = params.getGasLimit() ;
		} catch (Exception e) {
			log.error("Error.initVars", e);
			log.error(e.getMessage());
		}
	}

	private String generatePrivateKey(EthCoreParams params) {
		String privateKey = null;
		try {
			privateKey = params.getPrivateKey() != null ? params.getPrivateKey() : createCredentials(params.getSeed()).getPrivateKey();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return privateKey;
	}

	public Web3 getWeb3jInstance() {
		return web3;
	}

//	BesuPrivateTransactionManager getTransactionManagerBesu() {
//		return transactionManager;
//	}

	public ContractGasProvider getDefaultContractGasProvider() throws IOException {
		ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
		return contractGasProvider;
	}

//	private BesuPrivacyGasProvider getDefaultBesuPrivacyGasProvider() {
//		BesuPrivacyGasProvider besuPrivacyGasProvider = new BesuPrivacyGasProvider(gasPrice, gasLimit);
//		return besuPrivacyGasProvider;
//	}

	public Account createCredentials(String seed) throws CipherException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, NoSuchProviderException {
		ECKeyPair ecKeyPair = Keys.createEcKeyPair();
		BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
		BigInteger publicKeyInDec = ecKeyPair.getPublicKey();
		String privatekeyInHex = privateKeyInDec.toString(16);
		String publickeyInHex = publicKeyInDec.toString(16);
		WalletFile wallet = Wallet.createLight(seed, ecKeyPair);
		String address = wallet.getAddress();
		Account account = new Account("0x" + address, "0x" + privatekeyInHex, publickeyInHex);
		return account;
	}

//	private void updatePrivateKey(String privateKey) {
//		credentials = Credentials.create(privateKey);
//	}

	public Credentials getCredentials() {
		return credentials;
	}

	public String getNetworkId() {
		return networkId;
	}

	public BigInteger getGasPrice() throws IOException {
		EthGasPrice ethGasPrice = web3.ethGasPrice().send();
		BigInteger gasPrice = ethGasPrice.getGasPrice();
		return gasPrice;
	}

//	public BigInteger getGasLimit() throws IOException {
//		BigInteger blockGasLimit = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock()
//				.getGasLimit();
//		return blockGasLimit;
//	}

//	public BigInteger getNonce(@Nullable String address) throws InterruptedException, ExecutionException {
//		String addressAccount = address != null ? address : credentials.getAddress();
//		EthGetTransactionCount ethGetTransactionCount = web3
//				.ethGetTransactionCount(addressAccount, DefaultBlockParameterName.LATEST).sendAsync().get();
//		BigInteger nonce = ethGetTransactionCount.getTransactionCount();
//		return nonce;
//	}

	private OkHttpClient createOkHttpClient(boolean isSSL) {
		OkHttpClient.Builder builder = isSSL ? SslUtils.trustAllSslClient() : new OkHttpClient.Builder();

		// builder.retryOnConnectionFailure(true);
		// configureLogging(builder);
		configureTimeouts(builder);
		return builder.build();
	}

	private void configureTimeouts(OkHttpClient.Builder builder) {
		int tos = 30;

		builder.connectTimeout(tos, TimeUnit.SECONDS);
		builder.readTimeout(tos, TimeUnit.SECONDS); // Sets the socket timeout too
		builder.writeTimeout(tos, TimeUnit.SECONDS);

	}

//	private static void configureLogging(OkHttpClient.Builder builder) {
//		if (log.isDebugEnabled()) {
//			HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::debug);
//			logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//			builder.addInterceptor(logging);
//		}
//	}

	void closeConnection() {
		web3.shutdown();
//		besu.shutdown();
	}

}
