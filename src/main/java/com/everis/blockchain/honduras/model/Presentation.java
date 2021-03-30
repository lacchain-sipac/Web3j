package com.everis.blockchain.honduras.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Presentation {
	private List<VerifiableCredential> verifiableCredential;

	public Presentation() {
		verifiableCredential = new ArrayList<>();
	}
}
