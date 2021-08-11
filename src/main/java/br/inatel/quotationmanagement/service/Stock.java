package br.inatel.quotationmanagement.service;

public class Stock {

	private String id;
	private String description;
	
	public Stock() {}
	
	public Stock(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
}

