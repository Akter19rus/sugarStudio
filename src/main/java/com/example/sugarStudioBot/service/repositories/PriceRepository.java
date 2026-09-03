package com.example.sugarStudioBot.service.repositories;

import com.example.sugarStudioBot.service.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
}
