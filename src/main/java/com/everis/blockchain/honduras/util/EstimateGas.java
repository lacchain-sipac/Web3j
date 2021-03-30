package com.everis.blockchain.honduras.util;

public class EstimateGas {
	private String to;
	private String from;
	private String data;

	public EstimateGas(String from, String to, String data) {
		this.setFrom(from);
		this.setTo(to);
		this.setData(data);
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
