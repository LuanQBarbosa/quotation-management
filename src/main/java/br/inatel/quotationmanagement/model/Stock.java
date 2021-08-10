package br.inatel.quotationmanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String stockId;
	
	@OneToMany(mappedBy="stock")
	private List<StockQuote> quotes = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStockId() {
		return stockId;
	}
	
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
	public List<StockQuote> getQuotes() {
		return quotes;
	}
	
	public void setQuotes(List<StockQuote> quotes) {
		this.quotes = quotes;
	}

}
