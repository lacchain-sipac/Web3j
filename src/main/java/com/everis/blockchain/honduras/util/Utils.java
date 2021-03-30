package com.everis.blockchain.honduras.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.web3j.crypto.Hash;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Utils {
	public static  final String VERIFY_JWT_ERROR = "VERIFY_JWT_ERROR";
	public static  final String AUDIENCE_JWT_ERROR = "AUDIENCE_JWT_ERROR";
	public static  final String EXPIRED_JWT_ERROR = "EXPIRED_JWT_ERROR";
	

	public static  final String INVALID_ADDRESS_MNID_ERROR = "INVALID_ADDRESS_MNID_ERROR";

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

	public static byte[] getHashInByte(String hexData) {
		byte[] stringInByte = Numeric.hexStringToByteArray(hexData);
		return stringInByte;
	}

	public static byte[] stringToBytes(String string, int lenght) {
		byte[] byteValue = string.getBytes();
		byte[] byteValueLen = new byte[lenght];
		System.arraycopy(byteValue, 0, byteValueLen, 0, byteValue.length);
		return byteValueLen;
	}

	public static byte[] stringToBytes32Solidity(String string) {
		byte[] stringInByte = Numeric.hexStringToByteArray(Utils.asciiToHex(string));
		return stringInByte;
	}

	public static String bytes32ToString(byte[] param) throws DecoderException, UnsupportedEncodingException {
		byte[] bytes = Hex
				.decodeHex(Numeric.toHexString((byte[]) param).substring(2).replaceAll("00", "").toCharArray());
		return new String(bytes, "UTF-8");
	}

	public static List<byte[]> arrayStringToListBytes32Solidity(String[] list) {
		List<byte[]> listBytes = new ArrayList<byte[]>();
		int index = 0;
		for (String item : list) {
			listBytes.add(index, stringToBytes32Solidity(item));
			index++;
		}
		return listBytes;
	}

	public static ArrayList<String> arrayBytes32ToString(List list)
			throws UnsupportedEncodingException, DecoderException {
		ArrayList<String> listString = new ArrayList<String>();
		for (Object item : list) {
			byte[] bytes = Hex
					.decodeHex(Numeric.toHexString((byte[]) item).substring(2).replaceAll("00", "").toCharArray());
			listString.add(new String(bytes, "UTF-8"));
		}
		return listString;
	}

	public static String sha3String(String data) {
		return Hash.sha3String(data);
	}


	public static String asciiToHex(String asciiValue) {
		char[] chars = asciiValue.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString() + String.join("", Collections.nCopies(32 - (hex.length() / 2), "00"));
	}

	public static String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");
		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	
	public static String getHexBlank(String value) {
		return Strings.isEmpty(value) ? "0x0000000000000000000000000000000000000000000000000000000000000000" : value;
	}
	


	public static String getRevertReason(String revertReason) {
		
		String errorMethodId = "0x08c379a0";
		String errorsecond =  "008408c379a0";
	
		
		if (revertReason != null ) {
			revertReason = revertReason.replace(errorMethodId, "");
			revertReason = revertReason.replace(errorsecond, "");
			log.info(revertReason);
			return hexToAscii(revertReason).trim();
		}
		
		
		return revertReason;
	}

	public static String getRevertReason(Exception e) {
		if (e instanceof TransactionException) {
			TransactionException tx = (TransactionException) e;
			if ( tx.getTransactionReceipt() != null && tx.getTransactionReceipt().isPresent()) { //NOSONAR
				return Utils.getRevertReason(tx.getTransactionReceipt().get().getRevertReason());//NOSONAR
			}
		}
		return e != null ? e.getMessage() : "";
	}


	public static byte[] stringToBytes6Solidity(String string) {
		byte[] stringInByte = Numeric.hexStringToByteArray(asciiToHex6(string));
		return stringInByte;
	}
	
	private static String asciiToHex32(String asciiValue) {
		char[] chars = asciiValue.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString() + String.join("", Collections.nCopies(32 - (hex.length() / 2), "00"));
	}
	
	private static String asciiToHex6(String asciiValue) {
		char[] chars = asciiValue.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString() + String.join("", Collections.nCopies(6 - (hex.length() / 2), "00"));
	}
}
