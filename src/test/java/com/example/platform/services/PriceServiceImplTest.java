package com.example.platform.services;

import com.example.platform.dtos.GetPriceResponseDTO;
import com.example.platform.exceptions.BrandNotFoundException;
import com.example.platform.exceptions.ProductNotFoundException;
import com.example.platform.models.Brand;
import com.example.platform.models.Price;
import com.example.platform.models.Product;
import com.example.platform.repository.BrandRepository;
import com.example.platform.repository.PriceRepository;
import com.example.platform.repository.ProductRepository;
import com.example.platform.services.impl.PriceServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.platform.utils.DateUtils.parseStringToLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PriceServiceImplTest {
    @Mock
    BrandRepository brandRepository;

    @Mock
    PriceRepository priceRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    Price price1;
    Product dressProduct;
    Brand zaraBrand;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    public void whenFindPriceWithAnInvalidProductId_ThenReturnAnException(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> priceService.getPrice("applicationDateTime", 1L, 2L));
    }

    @Test
    public void whenFindPriceWithAnInvalidBrandId_ThenReturnAnException(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(dressProduct));
        when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BrandNotFoundException.class, () -> priceService.getPrice("applicationDateTime", 1L, 2L));
    }

    @Test
    public void whenFindPriceWithoutPriceForTheProduct_ThenReturnAnEmptyResponse(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(dressProduct));
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(zaraBrand));
        when(priceRepository.findByApplicationDateTimeAndProductIDAndBrandID(parseStringToLocalDateTime("2020-06-14T15.00.00"),1L,2L))
                .thenReturn(null);
        assertNull(priceService.getPrice("2020-06-14T15.00.00", 1L, 2L));
    }

    @Test
    public void whenFindPriceWithValidData_ThenReturnOk(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(dressProduct));
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(zaraBrand));
        when(priceRepository.findByApplicationDateTimeAndProductIDAndBrandID(parseStringToLocalDateTime("2020-06-14T15.00.00"),1L,2L))
                .thenReturn(price1);
        GetPriceResponseDTO responseExpected = new GetPriceResponseDTO().build(price1);
        assertTrue(responseExpected.equals(priceService.getPrice("2020-06-14T15.00.00", 1L, 2L)));
    }

}
