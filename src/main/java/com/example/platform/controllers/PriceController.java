package com.example.platform.controllers;

import com.example.platform.dtos.GetPriceResponseDTO;
import com.example.platform.services.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0")
public class PriceController {
    @Autowired
    IPriceService priceService;

    @GetMapping(path = "/price")
    public ResponseEntity <Object> getPrice(@RequestParam String applicationDateTime,
                                                       @RequestParam Long productID,
                                                       @RequestParam Long brandID ){
        try {
            GetPriceResponseDTO price = priceService.getPrice(applicationDateTime, productID,brandID);
            return new ResponseEntity<>(price, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
