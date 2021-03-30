package com.everis.blockchain.honduras.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtBean {

	/* Identifies which algorithm is used to generate the signature */
	private JwtHeader header;

	/* Contains a set of claims */
	private JwtPayload payload;
	/*
	 * Securely validates the token. The signature is calculated by encoding the
	 * header and payload using Base64url Encoding and concatenating the two
	 * together with a period (.) separator
	 */
	private String signature;
	
	public JwtBean() {
		header = new JwtHeader();
		payload = new JwtPayload();
	}
}
