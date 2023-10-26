package com.example.platform.repository;

import com.example.platform.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PriceRepository extends JpaRepository<Price,Long> {
    @Query(value=
    "SELECT TOP 1 * FROM PRICE " +
    " WHERE brand_id = :brandID " +
    "   AND product_id = :productID " +
    "   AND :applicationDateTimeToQuery BETWEEN start_date and end_date " +
    " ORDER BY priority DESC ", nativeQuery = true)
    Price findByApplicationDateTimeAndProductIDAndBrandID(LocalDateTime applicationDateTimeToQuery, Long productID, Long brandID);
}
