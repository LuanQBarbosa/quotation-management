package br.inatel.quotationmanagement.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.inatel.quotationmanagement.model.Operation;
import br.inatel.quotationmanagement.model.Quote;

public class OperationForm {
	
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
	
	public List<Quote> generateQuoteList(Operation operation) {
		List<Quote> quotes = new ArrayList<>();
		
		for (Map.Entry<String, String> quoteEntry : this.quotes.entrySet()) {
			Quote quote = new Quote();
			quote.setDate(LocalDate.parse(quoteEntry.getKey()));
			quote.setValue(new BigDecimal(quoteEntry.getValue()));
			quote.setOperation(operation);
			
			quotes.add(quote);
		}
		
		return quotes;
	}
	
	public boolean isQuotesDatesValid() {
		for (Map.Entry<String, String> quoteEntry : this.quotes.entrySet()) {
			String date = quoteEntry.getKey();
			
			if (date.isEmpty() || !date.matches("^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isQuotesValuesValid() {
		for (Map.Entry<String, String> quoteEntry : this.quotes.entrySet()) {
			String value = quoteEntry.getValue();
			
			if (value.isEmpty() || !value.matches("^[0-9]*([\\\\.,]{1}[0-9]{0,2}){0,1}$")) {
				return false;
			}
		}
		
		return true;
	}

}
