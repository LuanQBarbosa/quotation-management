package br.inatel.quotationmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
	
	List<Stock> findByStockId(String stockId);

}
