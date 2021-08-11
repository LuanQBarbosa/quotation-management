package br.inatel.quotationmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Operation;

public interface OperationRepository extends JpaRepository<Operation, String> {
	
	List<Operation> findByStockId(String stockId);

}
