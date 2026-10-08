package com.example.dex_gui.controller;

import com.example.dex_gui.dto.ApiResponse;
import com.example.dex_gui.dto.ExistQuartetResponse;
import com.example.dex_gui.entity.Quartet;
import com.example.dex_gui.repository.QuartetRepository;
import com.example.dex_gui.service.QuartetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/quartet")
public class QuartetController {

    @Autowired
    private QuartetService quartetService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Quartet>> createQuartet(@RequestBody Quartet quartet) throws IOException {
        Quartet createdQuartet = quartetService.createQuartet(quartet);
        ApiResponse<Quartet> response = new ApiResponse<>(
                "Quartet successfully created",
                createdQuartet
        );
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<ExistQuartetResponse>> searchQuartet(@RequestBody Quartet quartet){
        boolean isQuartetFound = quartetService.searchQuartet(quartet);
        ExistQuartetResponse eq = new ExistQuartetResponse();
        eq.setMessage(isQuartetFound ? "Quartet found successfully" : "Quartet not found");
        eq.setExist(isQuartetFound);
        ApiResponse<ExistQuartetResponse> response = new ApiResponse<>(
                isQuartetFound ? "Quartet found successfully" : "Quartet not found",
                eq
        );
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Quartet>>> getAllQuartets(){
        List<Quartet> foundedQuartets = quartetService.findAllQuartets();

        ApiResponse<List<Quartet>> response = new ApiResponse<>(
                "Quartet successfully fetched",
                foundedQuartets
        );
        return ResponseEntity.status(200).body(response);
    }
}
