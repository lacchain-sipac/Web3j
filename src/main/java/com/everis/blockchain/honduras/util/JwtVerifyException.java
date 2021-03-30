package com.everis.blockchain.honduras.util;

public class JwtVerifyException extends Exception{
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1254272650523588698L;

	public JwtVerifyException(String message) {
		super(message);
	}
	
	public JwtVerifyException( ) {
		super( );
	}

}
