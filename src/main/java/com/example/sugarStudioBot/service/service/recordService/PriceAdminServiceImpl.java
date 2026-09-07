package com.example.sugarStudioBot.service.service.recordService;

import com.example.sugarStudioBot.service.model.Price;
import com.example.sugarStudioBot.service.repositories.PriceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class PriceAdminServiceImpl implements PriceAdminService {

    private final PriceRepository priceRepository;

    public void updatePrice(String serviceName, BigDecimal newPriceService) {
        Price price = priceRepository.findByNameService(serviceName)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));

        price.setPrice(newPriceService);
        priceRepository.save(price);
    }
}
