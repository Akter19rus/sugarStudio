package com.example.sugarStudioBot.service.service.recordService;

import java.math.BigDecimal;

public interface PriceAdminService{
    void updatePrice(String serviceName, BigDecimal newPrice);
}
