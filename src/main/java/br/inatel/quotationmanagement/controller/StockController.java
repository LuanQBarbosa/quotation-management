package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.List;

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
import br.inatel.quotationmanagement.controller.dto.StockDto;
import br.inatel.quotationmanagement.controller.form.StockForm;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.repository.StockRepository;

@RestController
@RequestMapping("/stocks")
public class StockController {
	
	@Autowired
	private StockRepository stockRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid StockForm form, UriComponentsBuilder uriBuilder) {		
		Stock stock = new Stock();
		stock.setStockId(form.getStockId());
		
		List<Quote> quotes = form.generateQuoteList(stock);
		stock.setQuotes(quotes);
		
		stock = stockRepository.save(stock);
		
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(form.getStockId()).toUri();
		return ResponseEntity.created(uri).body(new StockDto(stock));
	}
	
	@GetMapping("/{stockId}")
	public ResponseEntity<?> getByStockId(@PathVariable("stockId") String stockId) {
		List<Stock> stockList = stockRepository.findByStockId(stockId);
		
		if(stockList.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorFormDto("stockId", "No quotes found with the requested stock id : " + stockId));
		}
		
		return ResponseEntity.ok(StockDto.convertToList(stockList));
	}
	
	@GetMapping()
	public ResponseEntity<?> list() {
		List<Stock> stockList = stockRepository.findAll();
		
		if(stockList.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorFormDto("", "No stocks registered on database yet"));
		}
		
		return ResponseEntity.ok(StockDto.convertToList(stockList));
	}
	
}
