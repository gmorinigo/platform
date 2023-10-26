package com.example.platform.services.impl;

import com.example.platform.constants.ErrorConstants;
import com.example.platform.dtos.GetPriceResponseDTO;
import com.example.platform.exceptions.BrandNotFoundException;
import com.example.platform.exceptions.ProductNotFoundException;
import com.example.platform.models.Price;
import com.example.platform.models.Product;
import com.example.platform.repository.BrandRepository;
import com.example.platform.repository.PriceRepository;
import com.example.platform.repository.ProductRepository;
import com.example.platform.services.IPriceService;
import com.example.platform.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceServiceImpl implements IPriceService {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ProductRepository productRepository;

    public PriceServiceImpl(BrandRepository brandRepository, PriceRepository priceRepository,
                            ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
    }
    @Override
    public GetPriceResponseDTO getPrice(String applicationDateTime, Long productID, Long brandID){
        productRepository.findById(productID).orElseThrow(() -> new ProductNotFoundException(ErrorConstants.PRODUCT_NOT_FOUND_ERROR_MESSAGE + productID));
        brandRepository.findById(brandID).orElseThrow(() -> new BrandNotFoundException(ErrorConstants.BRAND_NOT_FOUND_ERROR_MESSAGE + brandID));
        LocalDateTime applicationDateTimeToQuery = DateUtils.parseStringToLocalDateTime(applicationDateTime);
        Price price = priceRepository.findByApplicationDateTimeAndProductIDAndBrandID(applicationDateTimeToQuery,productID,brandID);
        if (price == null) return null;
        return new GetPriceResponseDTO().build(price);
    }
}
