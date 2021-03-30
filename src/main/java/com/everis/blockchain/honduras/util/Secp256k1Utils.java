package com.everis.blockchain.honduras.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.time.Instant;
import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

import com.everis.blockchain.honduras.main.model.Constants;
import com.everis.blockchain.honduras.model.JwtBean;
import com.everis.blockchain.honduras.model.JwtHeader;
import com.everis.blockchain.honduras.model.JwtPayload;
import com.everis.blockchain.honduras.model.Presentation;
import com.google.gson.Gson;

/**
 * 
 * @author Edgard Espinoza
 * @author everis s.a.
 * @version 1.0
 * 
 */
public class Secp256k1Utils {
	public static final String BEARER = "Bearer ";
	private static final String SECP256K1 = "secp256k1";
	private static final String ECDSA = "ECDSA";
	private static final String BC = "BC";

	/**
	 * GET PrivateKey from hex private
	 * 
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyFromPrivate(String privateKey) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		privateKey = privateKey.substring(2);

		org.bouncycastle.jce.spec.ECNamedCurveParameterSpec spec = org.bouncycastle.jce.ECNamedCurveTable
				.getParameterSpec(SECP256K1);

		java.security.KeyFactory kf = java.security.KeyFactory.getInstance("EC", BC);

		java.security.spec.ECParameterSpec paramSpec = new org.bouncycastle.jce.spec.ECNamedCurveSpec(spec.getName(),
				spec.getCurve(), spec.getG(), spec.getN());

		java.security.spec.ECPrivateKeySpec priSpec = new java.security.spec.ECPrivateKeySpec(
				new BigInteger(privateKey, 16), paramSpec);

//		org.bouncycastle.math.ec.ECPoint Q = spec.getG().multiply(new BigInteger(1, privateKey.getBytes())).normalize();
//		
//		ECPublicKeySpec pubSpec = new ECPublicKeySpec(
//				new ECPoint(Q.getAffineXCoord().toBigInteger(), Q.getAffineYCoord().toBigInteger()), paramSpec);
//		
//		this.publicKey = kf.generatePublic(pubSpec);

		return kf.generatePrivate(priSpec);

	}

	/**
	 * Get Public key coordinates (X + Y) from privateKey (hex)
	 * 
	 * @param privatehex
	 * @return String (X concatenated Y)
	 * @throws Exception
	 */
	public static String getPlubicKeyCoordFromPrivate(String privateKey) throws Exception {

		privateKey = privateKey.substring(2);

		org.bouncycastle.jce.spec.ECNamedCurveParameterSpec spec = org.bouncycastle.jce.ECNamedCurveTable
				.getParameterSpec(SECP256K1);
		org.bouncycastle.math.ec.ECPoint pointQn = spec.getG().multiply(new BigInteger(privateKey, 16)).normalize();

		return DatatypeConverter.printHexBinary(pointQn.getEncoded(false)).substring(2).toLowerCase();
	}

	/**
	 * Get public key form private key (hex)
	 * 
	 * @param privateKey
	 * @return PublicKey
	 * @throws Exception
	 */
	public static PublicKey getPlubicKeyFromPrivate(String privateKey) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		privateKey = privateKey.substring(2);

		org.bouncycastle.jce.spec.ECNamedCurveParameterSpec spec = org.bouncycastle.jce.ECNamedCurveTable
				.getParameterSpec(SECP256K1);

		java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance(ECDSA, BC);

		org.bouncycastle.math.ec.ECPoint pointQn = spec.getG().multiply(new BigInteger(privateKey, 16)).normalize();

		org.bouncycastle.jce.spec.ECPublicKeySpec pubSpec = new org.bouncycastle.jce.spec.ECPublicKeySpec(pointQn,
				spec);

		return keyFactory.generatePublic(pubSpec);
	}

	public static void generateKeyPair() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		java.security.spec.ECGenParameterSpec ecSpec = new java.security.spec.ECGenParameterSpec(SECP256K1);
		java.security.KeyPairGenerator g = java.security.KeyPairGenerator.getInstance("EC");
		g.initialize(ecSpec, new SecureRandom());
		java.security.KeyPair keyPair = g.generateKeyPair();

		PrivateKey privatekey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		System.out.println(privatekey.toString());
		System.out.println("-----------------");
		System.out.println(publicKey.toString());

	}

	/**
	 * Get address from public key
	 * 
	 * @param publicKey
	 * @return hex address
	 * @throws Exception
	 */
	public static String publicKeyToAddress(String publicKey) throws Exception {
		byte[] hashOfPublicKey = HashUtils.bytesToKeccak(DatatypeConverter.parseHexBinary(publicKey));

		byte[] slice = Arrays.copyOfRange(hashOfPublicKey, hashOfPublicKey.length - 20, hashOfPublicKey.length);

		return "0x" + Hex.toHexString(slice);
	}

	/**
	 * pubHex contains coordinates X and Y
	 * 
	 * @param pubHex
	 * @return PublicKey
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String pubHex) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		// pubHex add 0x04 followed by 32 bytes of X, then 32 bytes of Y.
		byte[] encoded = DatatypeConverter.parseHexBinary("04" + pubHex);

		org.bouncycastle.jce.spec.ECNamedCurveParameterSpec params = org.bouncycastle.jce.ECNamedCurveTable
				.getParameterSpec(SECP256K1);
		java.security.KeyFactory fact = java.security.KeyFactory.getInstance(ECDSA, BC);

		java.security.spec.EllipticCurve ellipticCurve = EC5Util.convertCurve(params.getCurve(), params.getSeed());
		java.security.spec.ECPoint point = ECPointUtil.decodePoint(ellipticCurve, encoded);
		java.security.spec.ECParameterSpec params2 = EC5Util.convertSpec(ellipticCurve, params);
		java.security.spec.ECPublicKeySpec keySpec = new java.security.spec.ECPublicKeySpec(point, params2);
		java.security.interfaces.ECPublicKey pk = (java.security.interfaces.ECPublicKey) fact.generatePublic(keySpec);
		return pk;
	}

	/**
	 * Verify that a JWT is signed correctly.
	 * 
	 * @param jwtToken
	 * @return JwtPayload
	 * @throws Exception
	 */
	public static JwtPayload verifyJWTokenSecp256k1(String jwtToken, String aud) throws Exception {

		if (jwtToken.startsWith(BEARER))
			jwtToken = jwtToken.replaceFirst(BEARER, "").trim();

		String[] split_string = jwtToken.trim().split("\\.");

		org.apache.commons.codec.binary.Base64 base64Url = new org.apache.commons.codec.binary.Base64(true);
		String dataJsonBody = new String(base64Url.decode(split_string[1]));

		Gson gson = new Gson();
		JwtPayload payload = gson.fromJson(dataJsonBody, JwtPayload.class);

		if (!validateTime(payload))
			throw new JwtExpirationException(Utils.EXPIRED_JWT_ERROR);

		// verify ecNumberSize = 32
		ECDSAAlgorithm ecdsaAlgorithm = new ECDSAAlgorithm("SHA256withECDSA", 32);

		boolean validate = ecdsaAlgorithm.verify(Secp256k1Utils.getPublicKey(payload.getIss()), split_string[0],
				split_string[1], split_string[2]);

		if (!validate)
			throw new JwtVerifyException(Utils.VERIFY_JWT_ERROR);

		// validate aud
		if (aud != null && "".equals(aud)) {
			aud = aud.replaceFirst("http://", "").replaceFirst("https://", "").trim();
			String audPayload = payload.getAud().replaceFirst("http://", "").replaceFirst("https://", "").trim();
			if (!audPayload.equals(aud))
				throw new AudienceException(Utils.AUDIENCE_JWT_ERROR);
		}
		return payload;

	}

	/**
	 * validate time jwt
	 * 
	 * @param payload
	 * @return
	 * @throws Exception
	 */
	private static boolean validateTime(JwtPayload payload) throws Exception {
		// 1579365383
		// 1590463607
		Instant instant = Instant.now();
		long timeStampSeconds = instant.getEpochSecond();

		return (timeStampSeconds <= payload.getExp() || payload.getExp() == 0);

	}

	public static String generateJwt(String privateKey, String did, String audience, Presentation presentation)
			throws Exception {

		ECDSAAlgorithm ecdsaAlgorithm = new ECDSAAlgorithm("SHA256withECDSA", 32);
		Gson gson = new Gson();
		Instant instant = Instant.now();
		long timeStampSeconds = instant.getEpochSecond();

		String pubkey = getPlubicKeyCoordFromPrivate(privateKey);

		JwtBean jwt = new JwtBean();
		jwt.getHeader().setAlg("ES256K");
		jwt.getHeader().setTyp("JWT");

		jwt.getPayload().setSub(did);
		jwt.getPayload().setIss(pubkey);
		jwt.getPayload().setAud(audience);
		jwt.getPayload().setPresentation(presentation);
		jwt.getPayload().setIat(timeStampSeconds);
		jwt.getPayload().setExp(timeStampSeconds + 50 * 60 * 60);

		String base64encodedHeader = java.util.Base64.getUrlEncoder().withoutPadding()
				.encodeToString(gson.toJson(jwt.getHeader(), JwtHeader.class).getBytes("utf-8"));

		String base64encodedPayload = java.util.Base64.getUrlEncoder().withoutPadding()
				.encodeToString(gson.toJson(jwt.getPayload(), JwtPayload.class).getBytes("utf-8"));

		byte[] sing = ecdsaAlgorithm.sign(getPrivateKeyFromPrivate(privateKey),
				base64encodedHeader.getBytes(StandardCharsets.UTF_8),
				base64encodedPayload.getBytes(StandardCharsets.UTF_8));

		String singSt = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(sing);

		return base64encodedHeader + "." + base64encodedPayload + "." + singSt;
	}

}
