package com.everis.blockchain.honduras.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mnid {
	private String network;
	private String address;
	
	public Mnid(String network, String address) {
		this.address = address;
		this.network = network;
	}
	public Mnid() {}
}
