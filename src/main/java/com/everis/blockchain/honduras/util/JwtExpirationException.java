package com.everis.blockchain.honduras.util;

public class JwtExpirationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6382432738861392677L;
	
	public JwtExpirationException(String message) {
		super(message);
	}
	
	public JwtExpirationException( ) {
		super( );
	}

}
