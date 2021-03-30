package com.everis.blockchain.honduras.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialSubject {
	
	@SerializedName(value = "type", alternate = "@type")
	private String type;
	
	@SerializedName(value = "id", alternate = "@id")
	private String id;
	
	@SerializedName(value = "email", alternate = "username")
	private String email;
	
	@SerializedName(value = "name")
	private  Object name;

}
