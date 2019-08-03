package com.example.reactiveproducer.repository;

import com.example.reactiveproducer.entity.Valuation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jakub krhovjak
 */
public interface ValuationRepository extends JpaRepository<Valuation, Long> {
}
