package com.everis.blockchain.honduras.http;

public class Account {

   public String address;
   public  String privateKey;
   public String publicKey;

    public Account(String add, String privk, String pubk) {
        address = add;
        privateKey = privk;
        publicKey = pubk;
    }
}