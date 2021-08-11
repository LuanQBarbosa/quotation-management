package br.inatel.quotationmanagement.controller.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.model.Operation;
import br.inatel.quotationmanagement.model.Quote;

public class OperationDto {
	
	private String id;
	private String stockId;
	private Map<String, String> quotes = new HashMap<>();
	
	public OperationDto(Operation operation) {
		this.id = operation.getId();
		this.stockId = operation.getStockId();
		
		for (Quote quote : operation.getQuotes()) {
			this.quotes.put(quote.getDate().toString(), quote.getValue().toString());
		}
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}
	
	public static List<OperationDto> convertToList(List<Operation> operations) {
		return operations.stream().map(OperationDto::new).collect(Collectors.toList());
	}

}
