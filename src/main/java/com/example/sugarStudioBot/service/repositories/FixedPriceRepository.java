package com.example.sugarStudioBot.service.repositories;

import com.example.sugarStudioBot.service.model.FixedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedPriceRepository extends JpaRepository<FixedPrice, Long> {
}
