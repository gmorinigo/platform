package com.example.platform;

import com.example.platform.models.Brand;
import com.example.platform.models.Price;
import com.example.platform.models.Product;
import com.example.platform.repository.BrandRepository;
import com.example.platform.repository.PriceRepository;
import com.example.platform.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static com.example.platform.utils.DateUtils.parseStringToLocalDateTime;

@SpringBootApplication
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(BrandRepository brandRepository, PriceRepository priceRepository,
									  ProductRepository productRepository) {
		return (args) -> {
			Brand zaraBrand = new Brand("Zara");
			brandRepository.save(zaraBrand);
			Product dressProduct = new Product("Dress");
			productRepository.save(dressProduct);

			this.generatePricesZaraDress(zaraBrand, dressProduct, priceRepository);
		};
	}

	private void generatePricesZaraDress(Brand zaraBrand, Product dressProduct, PriceRepository priceRepository) {
		priceRepository.save(Price.builder()
				.brand(zaraBrand)
				.startDate(parseStringToLocalDateTime("2020-06-14T00.00.00"))
				.endDate(parseStringToLocalDateTime("2020-12-31T23.59.59"))
				.priceList(1)
				.product(dressProduct)
				.priority(0)
				.price(BigDecimal.valueOf(35.50))
				.curr("EUR")
				.build());

		priceRepository.save(Price.builder()
				.brand(zaraBrand)
				.startDate(parseStringToLocalDateTime("2020-06-14T15.00.00"))
				.endDate(parseStringToLocalDateTime("2020-06-14T18.30.00"))
				.priceList(2)
				.product(dressProduct)
				.priority(1)
				.price(BigDecimal.valueOf(25.45))
				.curr("EUR")
				.build());

		priceRepository.save(Price.builder()
				.brand(zaraBrand)
				.startDate(parseStringToLocalDateTime("2020-06-15T00.00.00"))
				.endDate(parseStringToLocalDateTime("2020-06-15T11.00.00"))
				.priceList(3)
				.product(dressProduct)
				.priority(1)
				.price(BigDecimal.valueOf(30.50))
				.curr("EUR")
				.build());

		priceRepository.save(Price.builder()
				.brand(zaraBrand)
				.startDate(parseStringToLocalDateTime("2020-06-15T16.00.00"))
				.endDate(parseStringToLocalDateTime("2020-12-31T23.59.59"))
				.priceList(4)
				.product(dressProduct)
				.priority(1)
				.price(BigDecimal.valueOf(38.95))
				.curr("EUR")
				.build());
	}
}
