package com.everis.blockchain.honduras.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtHeader {

	/*
	 * Message authentication code algorithm
	 */
	private String alg;

	/*
	 * The "typ" (type) Header Parameter defined by [JWS] and [JWE] is used by JWT
	 * applications
	 */
	private String typ;
}
