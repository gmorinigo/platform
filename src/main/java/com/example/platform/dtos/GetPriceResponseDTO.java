package com.example.platform.dtos;

import com.example.platform.models.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPriceResponseDTO {
    private Long productID;
    private Long brandID;
    private Integer priceList;
    private LocalDateTime startDateApplied;
    private LocalDateTime endDateApplied;
    private BigDecimal price;

    public GetPriceResponseDTO build(Price price) {
        this.productID = price.getProduct().getId();
        this.brandID = price.getBrand().getId();
        this.priceList = price.getPriceList();
        this.startDateApplied = price.getStartDate();
        this.endDateApplied = price.getEndDate();
        this.price = price.getPrice();
        return this;
    }

    public boolean equals(GetPriceResponseDTO getPriceResponseDTO){
        return (this.productID == getPriceResponseDTO.getProductID() &&
            this.brandID == getPriceResponseDTO.getBrandID() &&
            this.priceList == getPriceResponseDTO.getPriceList() &&
            this.startDateApplied == getPriceResponseDTO.getStartDateApplied() &&
            this.endDateApplied == getPriceResponseDTO.getEndDateApplied() &&
            this.price == getPriceResponseDTO.getPrice());
    }
}
