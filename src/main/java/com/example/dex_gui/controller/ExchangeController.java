package com.example.dex_gui.controller;

import com.example.dex_gui.dto.ApiResponse;
import com.example.dex_gui.dto.ExchangeRequest;
import com.example.dex_gui.dto.ExchangeSearchRequest;
import com.example.dex_gui.entity.Exchange;
import com.example.dex_gui.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Exchange>> createExchange(
            @RequestBody ExchangeRequest request) {

        Exchange createdExchange =
                exchangeService.createExchange(request);

        ApiResponse<Exchange> response =
                new ApiResponse<>(
                        "Exchange created successfully",
                        createdExchange
                );

        return ResponseEntity
                .status(201)
                .body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Exchange>> searchExchange(
            @RequestBody ExchangeSearchRequest request) {

        Exchange exchange = exchangeService.searchExchange(request);

        ApiResponse<Exchange> response =
                new ApiResponse<>(
                        "Exchange found successfully",
                        exchange
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Exchange>>> getAllExchanges() {

        List<Exchange> exchanges =
                exchangeService.getAllExchanges();

        ApiResponse<List<Exchange>> response =
                new ApiResponse<>(
                        "Exchanges fetched successfully",
                        exchanges
                );

        return ResponseEntity.ok(response);
    }
}