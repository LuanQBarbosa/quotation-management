package br.inatel.quotationmanagement.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.model.Quote;

public class QuoteDto {
	
	private Long id;
	private String date;
	
	public QuoteDto(Quote quote) {
		this.id = quote.getId();
		this.date = quote.getDate().toString();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}
	
	public static List<QuoteDto> convertList(List<Quote> quotes) {
		return quotes.stream().map(QuoteDto::new).collect(Collectors.toList());
	}

}
