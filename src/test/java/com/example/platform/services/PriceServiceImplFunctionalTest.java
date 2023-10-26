package com.example.platform.services;

import com.example.platform.dtos.GetPriceResponseDTO;
import com.example.platform.models.Brand;
import com.example.platform.models.Price;
import com.example.platform.models.Product;
import com.example.platform.repository.BrandRepository;
import com.example.platform.repository.PriceRepository;
import com.example.platform.repository.ProductRepository;
import com.example.platform.services.impl.PriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static com.example.platform.utils.DateUtils.parseStringToLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PriceServiceImplFunctionalTest {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ProductRepository productRepository;

    //@MockBean
    //private PriceServiceImpl priceService;
    IPriceService priceService;

    Price price1, price2, price3 , price4, price5;
    Product dressProduct;
    Brand zaraBrand;
    GetPriceResponseDTO responseExpected1;

    @BeforeEach
    void setUp() {
        priceService = new PriceServiceImpl(brandRepository, priceRepository, productRepository);
        zaraBrand = new Brand("Zara");
        dressProduct = new Product("Dress");
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
        price2 = Price.builder()
                .brand(zaraBrand)
                .startDate(parseStringToLocalDateTime("2020-06-14T15.00.00"))
                .endDate(parseStringToLocalDateTime("2020-06-14T18.30.00"))
                .priceList(2)
                .product(dressProduct)
                .priority(1)
                .price(BigDecimal.valueOf(25.45))
                .curr("EUR")
                .build();
        price3 = Price.builder()
                .brand(zaraBrand)
                .startDate(parseStringToLocalDateTime("2020-06-15T00.00.00"))
                .endDate(parseStringToLocalDateTime("2020-06-15T11.00.00"))
                .priceList(3)
                .product(dressProduct)
                .priority(1)
                .price(BigDecimal.valueOf(30.50))
                .curr("EUR")
                .build();
        price4 = Price.builder()
                .brand(zaraBrand)
                .startDate(parseStringToLocalDateTime("2020-06-15T16.00.00"))
                .endDate(parseStringToLocalDateTime("2020-12-31T23.59.59"))
                .priceList(4)
                .product(dressProduct)
                .priority(1)
                .price(BigDecimal.valueOf(38.95))
                .curr("EUR")
                .build();
        productRepository.save(dressProduct);
        responseExpected1 = new GetPriceResponseDTO().build(price1);
        brandRepository.save(zaraBrand);

        priceRepository.save(price1);
        priceRepository.save(price2);
        priceRepository.save(price3);
        priceRepository.save(price4);
    }

    @Test
    public void FunctionalTest1_ThenReturnOk(){
        GetPriceResponseDTO response = priceService.getPrice("2020-06-14T10.00.00", 1L, 1L);
        assertEquals(1, response.getPriceList());
        assertEquals(parseStringToLocalDateTime("2020-06-14T00.00.00"), response.getStartDateApplied());
        assertEquals(parseStringToLocalDateTime("2020-12-31T23.59.59"), response.getEndDateApplied());
        assertTrue(BigDecimal.valueOf(35.50).compareTo(response.getPrice())==0);
    }

    @Test
    public void FunctionalTest2_ThenReturnOk(){
        GetPriceResponseDTO response = priceService.getPrice("2020-06-14T16.00.00", 1L, 1L);
        assertEquals(2, response.getPriceList());
        assertEquals(parseStringToLocalDateTime("2020-06-14T15.00.00"), response.getStartDateApplied());
        assertEquals(parseStringToLocalDateTime("2020-06-14T18.30.00"), response.getEndDateApplied());
        assertTrue(BigDecimal.valueOf(25.45).compareTo(response.getPrice())==0);
    }

    @Test
    public void FunctionalTest3_ThenReturnOk(){
        GetPriceResponseDTO response = priceService.getPrice("2020-06-14T21.00.00", 1L, 1L);
        assertEquals(1, response.getPriceList());
        assertEquals(parseStringToLocalDateTime("2020-06-14T00.00.00"), response.getStartDateApplied());
        assertEquals(parseStringToLocalDateTime("2020-12-31T23.59.59"), response.getEndDateApplied());
        assertTrue(BigDecimal.valueOf(35.50).compareTo(response.getPrice())==0);
    }

    @Test
    public void FunctionalTest4_ThenReturnOk(){
        GetPriceResponseDTO response = priceService.getPrice("2020-06-15T10.00.00", 1L, 1L);
        assertEquals(3, response.getPriceList());
        assertEquals(parseStringToLocalDateTime("2020-06-15T00.00.00"), response.getStartDateApplied());
        assertEquals(parseStringToLocalDateTime("2020-06-15T11.00.00"), response.getEndDateApplied());
        assertTrue(BigDecimal.valueOf(30.50).compareTo(response.getPrice())==0);
    }

    @Test
    public void FunctionalTest5_ThenReturnOk(){
        GetPriceResponseDTO response = priceService.getPrice("2020-06-16T21.00.00", 1L, 1L);
        assertEquals(4, response.getPriceList());
        assertEquals(parseStringToLocalDateTime("2020-06-15T16.00.00"), response.getStartDateApplied());
        assertEquals(parseStringToLocalDateTime("2020-12-31T23.59.59"), response.getEndDateApplied());
        assertTrue(BigDecimal.valueOf(38.95).compareTo(response.getPrice())==0);
    }
}
