package br.inatel.quotationmanagement.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
	
	Quote findByOperationStockIdAndDate(String stockId, LocalDate date);

}
