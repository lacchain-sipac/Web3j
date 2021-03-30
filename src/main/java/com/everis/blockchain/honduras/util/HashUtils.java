package com.everis.blockchain.honduras.util;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;

public class HashUtils {
	
	public static byte[] bytesTosha3(byte[] bytes) {
		DigestSHA3 sha3256 = new SHA3.Digest256();
		sha3256.update(bytes);
		return sha3256.digest();
	}
	
	public static byte[] bytesTosha256(byte[] bytes) {
		SHA256.Digest sha256 = new SHA256.Digest();
		sha256.update(bytes);
		return sha256.digest();
	}
	
	public static byte[] bytesToKeccak(byte[] bytes) {
		Keccak.DigestKeccak kecc = new Keccak.Digest256();
		kecc.update(bytes);
		return kecc.digest();
	}
	
	public static String convetStringToHashKeccakHex(String secret) {
		byte[] cipher = bytesToKeccak(secret.getBytes());
		return "0x" + Hex.encodeHexString(cipher);
	}
	
	public static String convetStringToHashKeccakHex(byte[] bytes) {
		byte[] cipher = bytesToKeccak(bytes);
		return "0x" + Hex.encodeHexString(cipher);
	}
	
	public static String convetStringToHash3(byte[] bytes) {
		byte[] cipher = bytesTosha3(bytes);
		return "0x" + Hex.encodeHexString(cipher);
	}
	
	public static String convetStringToHash256(byte[] bytes) {
		byte[] cipher = bytesTosha256(bytes);
		return "0x" + Hex.encodeHexString(cipher);
	}

}
