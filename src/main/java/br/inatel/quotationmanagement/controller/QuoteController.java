package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.quotationmanagement.config.validation.ErrorFormDto;
import br.inatel.quotationmanagement.controller.dto.OperationDto;
import br.inatel.quotationmanagement.controller.form.OperationForm;
import br.inatel.quotationmanagement.model.Operation;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.repository.OperationRepository;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.service.Stock;
import br.inatel.quotationmanagement.service.StockService;

@RestController
@RequestMapping("/quotes")
public class QuoteController {
	
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private QuoteRepository quoteRepository;
	@Autowired
	private StockService stockService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid OperationForm form, UriComponentsBuilder uriBuilder) {
		List<Stock> stockList = stockService.getStockList();
		List<String> stockIdList = stockList.stream().map(Stock::getId).collect(Collectors.toList());
		
		if (!stockIdList.contains(form.getStockId())) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorFormDto("stockId", "There is no stock registered with the id : " + form.getStockId()));
		}
		
		if(!form.isQuotesDatesValid()) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorFormDto("quotes", "Invalid quote date found"));
		}
		
		if(!form.isQuotesValuesValid()) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorFormDto("quotes", "Invalid quote value found"));
		}
		
		for (Map.Entry<String, String> quoteEntry : form.getQuotes().entrySet()) {
			Quote existingQuote = quoteRepository.findByOperationStockIdAndDate(form.getStockId(), LocalDate.parse(quoteEntry.getKey()));
			
			if (existingQuote != null) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorFormDto("quotes", "There is already a quote registered on the date " + quoteEntry.getKey() + " for the stock of id : " + form.getStockId()));
			}
		}
		
		Operation operation = new Operation();
		operation.setStockId(form.getStockId());
		
		List<Quote> quotes = form.generateQuoteList(operation);
		operation.setQuotes(quotes);
		
		operation = operationRepository.save(operation);
		
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(form.getStockId()).toUri();
		return ResponseEntity.created(uri).body(new OperationDto(operation));
	}
	
	@GetMapping("/{stockId}")
	public ResponseEntity<?> getByStockId(@PathVariable("stockId") String stockId) {
		List<Operation> operationList = operationRepository.findByStockId(stockId);
		
		if(operationList.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorFormDto("stockId", "No quotes found with the requested stock id : " + stockId));
		}
		
		return ResponseEntity.ok(OperationDto.convertToList(operationList));
	}
	
	@GetMapping()
	public ResponseEntity<?> list() {
		List<Operation> operationList = operationRepository.findAll();
		
		if(operationList.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorFormDto("", "No operations registered on database yet"));
		}
		
		return ResponseEntity.ok(OperationDto.convertToList(operationList));
	}
	
}
