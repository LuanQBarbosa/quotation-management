package br.inatel.quotationmanagement.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockService {

	private RestTemplate restTemplate = new RestTemplate();
	private String stockManagerURL;
	
	@Autowired
	public StockService(@Value("${stock-manager.url}") String stockManagerURL) {
		this.stockManagerURL = stockManagerURL;
	}
	
	@Cacheable(value = "stocks")
	public List<Stock> getStockList() {
		log.info("Getting Stocks List from Stock Manager API");
		Stock[] stockList = restTemplate.getForObject(stockManagerURL + "/stock", Stock[].class);
		log.info("Stocks List successfully fetched from Stock Manager API");
		return Arrays.asList(stockList);
	}
	
	// @EventListener(ContextRefreshedEvent.class)
	@EventListener(ApplicationReadyEvent.class)
	public void register() {
		log.info("Registering Quotation Manager on Stock Manager API");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		JSONObject body = new JSONObject();
		body.put("host", "localhost");
		body.put("port", 8081);
		
		HttpEntity<String> request = new HttpEntity<String>(body.toString(), header);

		restTemplate.postForObject(stockManagerURL + "/notification", request, String.class);
		log.info("Quotation Manager successfully registered on Stock Manager API");
	}
	
}
