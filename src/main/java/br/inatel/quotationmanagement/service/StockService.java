package br.inatel.quotationmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {

	private RestTemplate restTemplate = new RestTemplate();
	private String stockManagerURL;
	
	@Autowired
	public StockService(@Value("${stock-manager.url}") String stockManagerURL) {
		this.stockManagerURL = stockManagerURL + "/stock";
	}
	
	public StockDescription getByStockId(String stockId) {
		return restTemplate.getForObject(stockManagerURL + "/" + stockId, StockDescription.class);
	}
	
}
