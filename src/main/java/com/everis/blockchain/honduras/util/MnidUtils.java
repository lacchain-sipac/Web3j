package com.everis.blockchain.honduras.util;

import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.util.encoders.Hex;

import com.google.common.primitives.Bytes;

public class MnidUtils {
	
	public static final String DID = "did:ev:";

	public static byte[] checksum(byte[] version, byte[] network, byte[] address) {
		byte[] bytesToSha3256 = HashUtils.bytesTosha3(Bytes.concat(version, network, address));
		return Arrays.copyOfRange(bytesToSha3256, 0, 4);

	}

	public static String encode(String network, String address) {
		byte[] versionByte = DatatypeConverter.parseHexBinary("01");
		byte[] networkByte = DatatypeConverter.parseHexBinary("0" + network.substring(2));
		byte[] addressByte = DatatypeConverter.parseHexBinary(address.substring(2));
		byte[] checksumByte = checksum(versionByte, networkByte, addressByte);
		byte[] concatByte = Bytes.concat(versionByte, networkByte, addressByte, checksumByte);

		return Base58.encode(concatByte);
	}

	public static Mnid decode(String mnid) throws Exception {
		byte[] data = Base58.decode(mnid);

		int netLength = data.length - 24;
		byte[] version = Arrays.copyOfRange(data, 0, 1);
		byte[] network = Arrays.copyOfRange(data, 1, netLength);
		byte[] address = Arrays.copyOfRange(data, netLength, 20 + netLength);
		byte[] check = Arrays.copyOfRange(data, netLength + 20, data.length);

		if (Arrays.equals(check, checksum(version, network, address))) {
			return new Mnid("0x" + Hex.toHexString(network).substring(1), "0x" + Hex.toHexString(address));
		} else {
			throw new MnidInvalidAddressException(Utils.INVALID_ADDRESS_MNID_ERROR);
		}

	}


}
