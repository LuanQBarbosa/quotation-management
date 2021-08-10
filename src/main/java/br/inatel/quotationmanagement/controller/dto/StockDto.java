package br.inatel.quotationmanagement.controller.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.inatel.quotationmanagement.model.Stock;

public class StockDto {
	
	private Long id;
	private String stockId;
	private Map<String, String> quotes = new HashMap<>();
	
	public StockDto(Stock stock) {
		this.id = stock.getId();
		this.stockId = stock.getStockId();
		
		List<QuoteDto> quoteList = QuoteDto.convertList(stock.getQuotes());
		for (QuoteDto quote : quoteList) this.quotes.put(quote.getId().toString(), quote.getDate());
	}

	public Long getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}

}
