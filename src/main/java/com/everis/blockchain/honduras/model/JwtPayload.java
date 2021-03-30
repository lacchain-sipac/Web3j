package com.everis.blockchain.honduras.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtPayload {
	/*
	 * The "sub" (subject) claim identifies the principal that is the subject of the
	 * JWT
	 */
	private String sub;
	
	/* The "iss" (issuer) claim identifies the principal that issued the JWT */
	private String iss;
	
	/*
	 * The "iat" (issued at) claim identifies the time at which the JWT was issued
	 */
	private long iat;
	/*
	 * The "exp" (expiration time) claim identifies the expiration time on or after
	 * which the JWT MUST NOT be accepted for processing
	 */
	private long exp;


	/*
	 * The "aud" (audience) claim identifies the recipients that the JWT is intended
	 * for
	 */
	private String aud;



	/* Data to shared */
	private Presentation presentation;
	
	public JwtPayload() {
		presentation = new Presentation();
	} 
}
