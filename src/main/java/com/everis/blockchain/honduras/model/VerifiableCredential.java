package com.everis.blockchain.honduras.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifiableCredential {
	private CredentialSubject credentialSubject;
	
	public VerifiableCredential() {
		credentialSubject = new CredentialSubject();
	}
}
