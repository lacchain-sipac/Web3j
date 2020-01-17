package com.everis.blockchain.honduras.http;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.besu.Besu;
import org.web3j.protocol.besu.response.privacy.PrivGetTransactionReceipt;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.EthLog.LogObject;
import org.web3j.protocol.core.methods.response.EthLog.LogResult;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.BesuPrivateTransactionManager;
import org.web3j.tx.gas.BesuPrivacyGasProvider;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Base64String;

import com.everis.blockchain.honduras.util.SslUtils;
import com.everis.blockchain.honduras.util.Utils;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import okhttp3.OkHttpClient;

public class EthCore {

	private static final Logger log = LoggerFactory.getLogger(EthCore.class);
	private Web3j web3;
	private Besu besu;
	private String host;
	private Credentials credentials;
	private String networkId;
	private Base64String privacyGroupId;
	private Base64String privateFrom;
	private BesuPrivateTransactionManager transactionManager;
	public BigInteger gasPrice;
	public BigInteger gasLimit;

	public EthCore(EthCoreParams params, boolean isSSL) {
		web3 = Web3j.build(new HttpService(params.host, createOkHttpClient(isSSL), true));
		initVars(params);
	}

	public EthCore(EthCoreParams params, String privGroupId, String from, boolean isSSL) {
		besu = Besu.build(new HttpService(params.host, createOkHttpClient(isSSL), true));
		web3 = Web3j.build(new HttpService(params.host, createOkHttpClient(isSSL), true));
		host = params.host;
		initVars(params);
		privacyGroupId = Base64String.wrap(privGroupId);
		privateFrom = Base64String.wrap(from);
		transactionManager = new BesuPrivateTransactionManager(besu, getDefaultBesuPrivacyGasProvider(), credentials,
				Long.parseLong(networkId), privateFrom, privacyGroupId);
	}

	private void initVars(EthCoreParams params) {
		try {
			String privateKey = generatePrivateKey(params);
			credentials = Credentials.create(privateKey);
			networkId = web3.netVersion().send().getResult();
			host = params.host;
			gasPrice = params.gasPrice != null ? params.gasPrice : getGasPrice();
			gasLimit = params.gasLimit != null ? params.gasLimit : getGasLimit();
		} catch (Exception e) {
			log.error("Error.initVars", e);
			log.error(e.getMessage());
		}
	}

	private String generatePrivateKey(EthCoreParams params) {
		String privateKey = null;
		try {
			privateKey = params.privateKey != null ? params.privateKey : createCredentials(params.seed).privateKey;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return privateKey;
	}

	public Web3j getWeb3jInstance() {
		return web3;
	}

	Besu getBesuInstance() {
		return besu;
	}

	BesuPrivateTransactionManager getTransactionManagerBesu() {
		return transactionManager;
	}

	public ContractGasProvider getDefaultContractGasProvider() throws IOException {
		ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
		return contractGasProvider;
	}

	BesuPrivacyGasProvider getDefaultBesuPrivacyGasProvider() {
		BesuPrivacyGasProvider besuPrivacyGasProvider = new BesuPrivacyGasProvider(gasPrice, gasLimit);
		return besuPrivacyGasProvider;
	}

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

	public void updatePrivateKey(String privateKey) {
		credentials = Credentials.create(privateKey);
	}

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

	public BigInteger getGasLimit() throws IOException {
		BigInteger blockGasLimit = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock()
				.getGasLimit();
		return blockGasLimit;
	}

	public BigInteger getNonce(@Nullable String address) throws InterruptedException, ExecutionException {
		String addressAccount = address != null ? address : credentials.getAddress();
		EthGetTransactionCount ethGetTransactionCount = web3
				.ethGetTransactionCount(addressAccount, DefaultBlockParameterName.LATEST).sendAsync().get();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		return nonce;
	}

	public BigInteger getPrivNonce(@Nullable String address) throws IOException {
		String addressAccount = address != null ? address : credentials.getAddress();
		BigInteger nonce = besu.privGetTransactionCount(addressAccount, privacyGroupId).send().getTransactionCount();
		return nonce;
	}

	public PrivGetTransactionReceipt getPrivTransactionReceipt(String txHash, int retries)
			throws IOException, InterruptedException {
		PrivGetTransactionReceipt transactionReceipt = null;
		int cont = 0;
		while (cont <= retries) {
			transactionReceipt = besu.privGetTransactionReceipt(txHash).send();
			if (transactionReceipt.getResult() != null) {
				break;
			}
			cont++;
			Thread.sleep(1 * 1000);
		}
		return transactionReceipt;
	}

	TransactionReceipt getTransactionReceipt(String txHash, @Nullable Base64String orionPublicKey)
			throws ClientProtocolException, IOException {
		String orionPubKey = orionPublicKey == null ? privateFrom.toString() : orionPublicKey.toString();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(host);
		JSONArray params = new JSONArray().put(txHash).put(orionPubKey);
		String jsonRpc = new JSONObject().put("jsonrpc", "2.0").put("method", "priv_getTransactionReceipt")
				.put("id", generateRandomId()).put("params", params).toString();
		postRequest.setEntity(new StringEntity(jsonRpc));
		postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		CloseableHttpResponse httpResponse = httpClient.execute(postRequest);
		String contentString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject receipt = new JSONObject(contentString);
		if (!receipt.has("result") || receipt.isNull("result"))
			return null;
		JSONObject result = receipt.getJSONObject("result");
		List<Log> logs = formatJsonLogs(result.getJSONArray("logs"));
		String transactionIndex = null;
		String blockHash = null;
		String blockNumber = null;
		String cumulativeGasUsed = null;
		String gasUsed = null;
		String root = null;
		String status = null;
		String logsBloom = null;
		String from = result.getString("from");
		String to = result.getString("to");
		String contractAddress = null;
		if (result.has("contractAddress") && !result.isNull("contractAddress")) {
			contractAddress = result.getString("contractAddress");
		}
		for (Log item : logs) {
			LogObject i = (LogObject) item;
			transactionIndex = i.getTransactionIndexRaw();
			blockHash = i.getBlockHash();
			blockNumber = i.getBlockNumberRaw();
			break;
		}
		TransactionReceipt transactionReceipt = new TransactionReceipt(txHash, transactionIndex, blockHash, blockNumber,
				cumulativeGasUsed, gasUsed, contractAddress, root, status, from, to, logs, logsBloom);
		return transactionReceipt;
	}

	public List<LogResult> getLogsEventContract(@Nullable BigInteger fromBlock, @Nullable BigInteger toBlock,
			String contractAddress, @Nullable String event) throws IOException {
		DefaultBlockParameter fromblock = fromBlock != null ? DefaultBlockParameter.valueOf(fromBlock)
				: DefaultBlockParameterName.EARLIEST;
		DefaultBlockParameter toblock = toBlock != null ? DefaultBlockParameter.valueOf(toBlock)
				: DefaultBlockParameterName.LATEST;
		EthFilter filter = new EthFilter(fromblock, toblock, contractAddress);
		String hashed = Utils.sha3String(event); // event format example: "MyEvent(string,string)"
		if (event != null)
			filter.addSingleTopic(hashed);
		EthLog responseLogs = web3.ethGetLogs(filter).send();
		List<LogResult> logs = responseLogs.getLogs();
		return logs;
	}

	Flowable<Transaction> subscribeLogsEventContract() throws IOException {
		return web3.transactionFlowable();
	}

	public List<Log> getLogs(BigInteger fromBlock, @Nullable BigInteger toBlock, int retries, String from)
			throws IOException, InterruptedException {
		Block lastBlock = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
		BigInteger toblock = toBlock != null ? toBlock : lastBlock.getNumber();
		List<Log> logs = new ArrayList<Log>();
		for (int i = fromBlock.intValue(); i <= toblock.intValue(); i++) {
			EthBlock block = web3.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), false).send();
			for (TransactionResult tr : block.getResult().getTransactions()) {
				PrivGetTransactionReceipt receipt = getPrivTransactionReceipt(tr.get().toString(), retries);
//				log.info("from "+from);
//				log.info("receipt "+receipt);
//				log.info("receipt.getResult() " + receipt.getResult());
				if (receipt.getResult() != null && !from.equals(receipt.getResult().getFrom())) {
					logs.addAll(receipt.getResult().getLogs());
				}
			}
		}
		return logs;
	}

	public List<Log> getLogsEvent(@Nullable String fromBlock, @Nullable String toBlock,
			@Nullable String contractAddress, String event, @Nullable String... topics) throws IOException {
		String hashed = Utils.sha3String(event); // event format example: "MyEvent(string,string)"
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(host);
		JSONObject params = new JSONObject();
		if (contractAddress != null)
			params.put("address", contractAddress);
		if (fromBlock != null)
			params.put("fromBlock", fromBlock);
		if (toBlock != null)
			params.put("toBlock", toBlock);
		JSONArray topicsArray = new JSONArray();
		JSONArray topicsFilter = new JSONArray();
		topicsArray.put(new JSONArray().put(hashed));
		if (topics != null) {
			for (String topic : topics) {
				topicsFilter.put(topic);
			}
		}
		topicsArray.put(topicsFilter);
		params.put("topics", topicsArray);
		String jsonString = new JSONObject().put("jsonrpc", "2.0").put("method", "eth_getLogs")
				.put("id", generateRandomId()).put("params", new JSONArray().put(params)).toString();
		postRequest.setEntity(new StringEntity(jsonString));
		postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		CloseableHttpResponse httpResponse = httpClient.execute(postRequest);
		String contentString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject content = new JSONObject(contentString);
		if (!content.has("result") || content.isNull("result"))
			return null;
		if (content.getJSONArray("result").isEmpty())
			return null;
		JSONArray logsJson = content.getJSONArray("result");
		if (logsJson.length() == 0)
			return null;
		List<Log> logs = formatJsonLogs(logsJson);
		return logs;
	}

	private List<Log> formatJsonLogs(JSONArray logsJson) {
		List<Log> logs = new ArrayList<Log>();
		for (int i = 0; i < logsJson.length(); i++) {
			JSONObject logInfo = logsJson.getJSONObject(i);
			boolean removed = logInfo.getBoolean("removed");
			String logIndex = logInfo.getString("logIndex");
			String transactionIndex = logInfo.getString("transactionIndex");
			String transactionHash = logInfo.getString("transactionHash");
			String blockHash = logInfo.getString("blockHash");
			String blockNumber = logInfo.getString("blockNumber");
			String address = logInfo.getString("address");
			String data = logInfo.getString("data");
			String type = null;
			JSONArray topicsJsonArray = logInfo.getJSONArray("topics");
			List<String> topicsList = new ArrayList<String>();
			for (int j = 0; j < topicsJsonArray.length(); j++) {
				topicsList.add(topicsJsonArray.getString(j));
			}
			Log log = new LogObject(removed, logIndex, transactionIndex, transactionHash, blockHash, blockNumber,
					address, data, type, topicsList);
			logs.add(log);
		}
		return logs;
	}

	private int generateRandomId() {
		int max = 1000;
		int min = 1;
		return (int) (Math.random() * ((max - min) + 1)) + min;
	}

	private OkHttpClient createOkHttpClient(boolean isSSL) {
		OkHttpClient.Builder builder = isSSL ? SslUtils.trustAllSslClient() : new OkHttpClient.Builder();

		// builder.retryOnConnectionFailure(true);
		// configureLogging(builder);
		configureTimeouts(builder);
		return builder.build();
	}

	private void configureTimeouts(OkHttpClient.Builder builder) {
		Long tos = 100L;
		if (tos != null) {
			builder.connectTimeout(tos, TimeUnit.SECONDS);
			builder.readTimeout(tos, TimeUnit.SECONDS); // Sets the socket timeout too
			builder.writeTimeout(tos, TimeUnit.SECONDS);
		}
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
		besu.shutdown();
	}

}
