package com.everis.blockchain.honduras.util;

import java.io.IOException;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.web3j.crypto.Hash;
 

public class Utils {

	public static byte[] calculateHash(String data) {
		byte[] hash = Hash.sha256(data.getBytes());
		return hash;
	}

	public static String hashByteToString(byte[] hash) {
		return "0x" + Hex.encodeHexString(hash);
	}

	public static String calculateHashString(String data) {
		byte[] hash = calculateHash(data);
		return hashByteToString(hash);
	}

	public static byte[] stringToBytes(String string, int lenght) {
		byte[] byteValue = string.getBytes();
		byte[] byteValueLen = new byte[lenght];
		System.arraycopy(byteValue, 0, byteValueLen, 0, byteValue.length);
		return byteValueLen;
	}

	public static String sha3String(String data) {
		return Hash.sha3String(data);
	}

	public static String encodeMnid(String network, String address, String host)
			throws ClientProtocolException, IOException {
		String mnidURL = host; // https://35.243.210.53/api
		CloseableHttpClient httpClient = getHttpClient();
		HttpPost postRequest = new HttpPost(mnidURL + "/mnid-encode");
		String params = new JSONObject().put("network", network).put("address", address).toString();
		postRequest.setEntity(new StringEntity(params));
		postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		CloseableHttpResponse httpResponse = httpClient.execute(postRequest);
		String contentString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject content = new JSONObject(contentString);
		String mnid = content.getString("mnid");
		return mnid;
	}

	public static JSONObject decodeMnid(String mnid, String host) throws ParseException, IOException {
		String mnidURL = host;
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet getRequest = new HttpGet(mnidURL + "/mnid-decode/" + mnid);
		CloseableHttpResponse httpResponse = httpClient.execute(getRequest);
		String contentString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject data = new JSONObject(contentString);
		return data;
	}

	public static JSONObject isValidMnid(String mnid, String host) throws ClientProtocolException, IOException {
		String mnidURL = host;
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet getRequest = new HttpGet(mnidURL + "/mnid-validate/" + mnid);
		CloseableHttpResponse httpResponse = httpClient.execute(getRequest);
		String contentString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject data = new JSONObject(contentString);
		return data;
	}

	private static CloseableHttpClient getHttpClient() {

		try {
//			SSLContext sslContext = SSLContext.getInstance("SSL");
//
//			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
//				public X509Certificate[] getAcceptedIssuers() {
//					return null;
//				}
//
//				public void checkClientTrusted(X509Certificate[] certs, String authType) {
//				}
//
//				public void checkServerTrusted(X509Certificate[] certs, String authType) {
//				}
//			} }, new SecureRandom());
//
//			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
//					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//			CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();
//
//			return httpClient;

			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();

		} catch (Exception e) {
			e.printStackTrace();
			return HttpClientBuilder.create().build();
		}
	}
}
