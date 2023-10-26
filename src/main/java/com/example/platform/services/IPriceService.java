package com.example.platform.services;

import com.example.platform.dtos.GetPriceResponseDTO;

public interface IPriceService {
    GetPriceResponseDTO getPrice(String applicationDateTime, Long productID, Long brandID);
}
