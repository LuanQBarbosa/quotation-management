package br.inatel.quotationmanagement.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;

public class StockForm {
	
	@NotNull
	@NotEmpty
	private String stockId;
	
	@NotNull
	@NotEmpty
	private Map<String, String> quotes;

	public String getStockId() {
		return stockId;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}
	
	public List<Quote> generateQuoteList(Stock stock) {
		List<Quote> quotes = new ArrayList<>();
		for (Map.Entry<String, String> quoteEntry : this.quotes.entrySet()) {
			Quote quote = new Quote();
			quote.setDate(LocalDate.parse(quoteEntry.getKey()));
			quote.setValue(new BigDecimal(quoteEntry.getValue()));
			quote.setStock(stock);
			
			quotes.add(quote);
		}
		
		return quotes;
	}

}
