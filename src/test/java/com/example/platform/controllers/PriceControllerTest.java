package com.example.platform.controllers;

import com.example.platform.dtos.GetPriceResponseDTO;
import com.example.platform.models.Brand;
import com.example.platform.models.Price;
import com.example.platform.models.Product;
import com.example.platform.services.IPriceService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.example.platform.utils.DateUtils.parseStringToLocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    IPriceService priceService;

    Price price1;

    @BeforeEach
    void setUp() {
        Brand zaraBrand = new Brand("Zara");
        Product dressProduct = new Product("Dress");
        price1 = Price.builder()
                .brand(zaraBrand)
                .startDate(parseStringToLocalDateTime("2020-06-14T00.00.00"))
                .endDate(parseStringToLocalDateTime("2020-12-31T23.59.59"))
                .priceList(1)
                .product(dressProduct)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .curr("EUR")
                .build();
    }

    @SneakyThrows
    @Test
    void whenGetPrice_thenReturnStatusOk() {

        when(priceService.getPrice(any(),any(),any())).thenReturn(new GetPriceResponseDTO().build(price1));

        mvc.perform(get("/api/v0/price" + "?applicationDateTime=2020-06-14T10.00.00&productID=12&brandID=1"))
                .andExpect(status().isOk());

        verify(priceService, times(1)).getPrice(any(),any(),any());

    }
}
