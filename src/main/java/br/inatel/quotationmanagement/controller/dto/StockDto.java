package br.inatel.quotationmanagement.controller.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;

public class StockDto {
	
	private Long id;
	private String stockId;
	private Map<String, String> quotes = new HashMap<>();
	
	public StockDto(Stock stock) {
		this.id = stock.getId();
		this.stockId = stock.getStockId();
		
		for (Quote quote : stock.getQuotes()) {
			this.quotes.put(quote.getDate().toString(), quote.getValue().toString());
		}
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
	
	public static List<StockDto> convertToList(List<Stock> stocks) {
		return stocks.stream().map(StockDto::new).collect(Collectors.toList());
	}

}
