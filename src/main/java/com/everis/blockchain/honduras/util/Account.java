package com.everis.blockchain.honduras.util;

public class Account {

   private String address;
   private  String privateKey;
   private String publicKey;

    public Account(String add, String privk, String pubk) {
        address = add;
        privateKey=privk;
        publicKey = pubk;
    }

	public String getPrivateKey() {
		return privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getAddress() {
		return address;
	}

}