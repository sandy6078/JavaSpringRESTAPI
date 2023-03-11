package com.javaspringrestapi.demo.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	List<CustomerOrder> findByClientId(Long clientId);
	List<CustomerOrder> findByBookId(Long bookId);
}