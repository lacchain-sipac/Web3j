package com.everis.blockchain.honduras;

import java.util.LinkedHashMap;

import org.junit.Test;

import com.everis.blockchain.honduras.main.model.GeneratePrivateKey;

public class TestgeneratePk {

	//@Test
	public void testGenerate() throws Exception {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("BLOCKCHAIN_SERVER", "http://eth-lacchain.kaytrust.id");

		new GeneratePrivateKey(map, null).create();

	}
}

//juntos peru
//0xaea4207e168df077538bf76426a1f2750dfcb426085d61499b9b7727fbe31c8b
//0x1f12050bb97c8590f08af629bdf954f293dd83f9

//tenaris
//0xc3f765bdbc849731bb8937c6bda3f506048e6ec1b71230d47b8c8e26876a12a3
//0x5d62f53fedf012dc832a3a09b6771a0f35cad0e0

//juntos brasil
//0x8cd5655428af70d5552370171c79ecf96f7822903b3b0d4012c1a1403fcca83f
//0x44c30a924d76fef8ffccb47d71dcd5df2d13e26b

//juntos test Peru
//0xa2ab3852c68d46413844e9779fb916f64559cc70cda9605e8beeb33b6fd9db34
//0xbbd12f889a400962bc0bc7d986d2c0e6b9b409d5

//test developer
//0x8ba8f23bafeea3df7e7158be6b34c9e86654ebbb3885fb507a6a3e0890e0fefc
//0x04252bed40834111be3e95d64022d437753a2fff

//one more
//0xdeeee85f7418abddc35c617a0f200fe2a02868121c6235a7f875849ffe8338c3
//0xfe5ee8e628928b35d494ebf1ce9ca206755b8016

