package br.inatel.quotationmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.inatel.quotationmanagement.controller.dto.OperationDto;
import br.inatel.quotationmanagement.model.Operation;
import br.inatel.quotationmanagement.model.Quote;
import org.junit.Assert;

public class OperationDtoTest {
	
	private Operation operation;
	private Map<String, String> quotes;
	private OperationDto operationDto;
	
	@BeforeEach
	void beforeEach() {
		String stockId = "petr4";
		this.operation = new Operation();
		operation.setStockId(stockId);
		
		quotes = new HashMap<>();
		quotes.put("2019-01-01", "10");
		quotes.put("2019-01-02", "11");
		
		List<Quote> quotesList = new ArrayList<>();
		for (Map.Entry<String, String> quoteEntry : this.quotes.entrySet()) {
			Quote quote = new Quote();
			quote.setDate(LocalDate.parse(quoteEntry.getKey()));
			quote.setValue(new BigDecimal(quoteEntry.getValue()));
			quote.setOperation(this.operation);
			
			quotesList.add(quote);
		}
		
		operation.setQuotes(quotesList);		
	}
	
	@Test
	void shouldCreateCorrectDto() {
		this.operationDto = new OperationDto(this.operation);
		
		Assert.assertEquals(this.operationDto.getId(), this.operation.getId());
		Assert.assertEquals(this.operationDto.getStockId(), this.operation.getStockId());
		Assert.assertTrue(this.operationDto.getQuotes().equals(this.quotes));
	}
	
	@Test
	void shouldGenerateSameLengthList() {
		List<Operation> operationList = new ArrayList<>();
		operationList.add(this.operation);
		
		List<OperationDto> operationDtoList = OperationDto.convertToList(operationList);
		
		Assert.assertEquals(operationList.size(), operationDtoList.size());
	}

}
