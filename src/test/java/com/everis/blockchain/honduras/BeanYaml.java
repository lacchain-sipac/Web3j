package com.everis.blockchain.honduras;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) 	
 public class BeanYaml {
	private String ipNodoAduna;
	private String addressPublicAduana;
	private String privateKeyAduana;
	private String addressContract;
	public  List<String> listBeanRol ;
	public  List<String> listProject;
	
	public BeanYaml() {
		listBeanRol = new ArrayList<>();
		listProject = new ArrayList<>();
	}
	
	public String getIpNodoAduna() {
		return ipNodoAduna;
	}
	public void setIpNodoAduna(String ipNodoAduna) {
		this.ipNodoAduna = ipNodoAduna;
	}
	public String getAddressPublicAduana() {
		return addressPublicAduana;
	}
	public void setAddressPublicAduana(String addressPublicAduana) {
		this.addressPublicAduana = addressPublicAduana;
	}
	public String getPrivateKeyAduana() {
		return privateKeyAduana;
	}
	public void setPrivateKeyAduana(String privateKeyAduana) {
		this.privateKeyAduana = privateKeyAduana;
	}
	public String getAddressContract() {
		return addressContract;
	}
	public void setAddressContract(String addressContract) {
		this.addressContract = addressContract;
	}
}
