package com.example.dex_gui.controller;

import com.example.dex_gui.dto.*;
import com.example.dex_gui.service.AutomationService;
import com.example.dex_gui.service.PythonPdfParserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {


    private final AutomationService automationService;
    private final PythonPdfParserService pythonPdfParserService;

    public AutomationController(AutomationService automationService, PythonPdfParserService pythonPdfParserService) {
        this.pythonPdfParserService = pythonPdfParserService;
        this.automationService = automationService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<AutomationFormData> analyze(
            @RequestParam("file") MultipartFile file) {

//        return ResponseEntity.ok(automationService.analyze(file));
//        return ResponseEntity.ok(automationService.analyzeOpenAI(file));
        return ResponseEntity.ok(automationService.analyzeText(file));
    }

    @PostMapping("/execution-plan")
    public ResponseEntity<AutomationExecutionPlan>
    createExecutionPlan(@RequestBody AutomationFormData data) {
        AutomationExecutionPlan plan = automationService.createExecutionPlan(data);
        return ResponseEntity.ok(plan);
    }

    @PostMapping("/execute")
    public ResponseEntity<AutomationExecutionResult> execute(@RequestBody AutomationExecutionPlan plan) {
        AutomationExecutionResult result = automationService.execute(plan);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/extract-pdf", consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<AutomationFormData> extractPdf(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(200).body( pythonPdfParserService.extractFormData(file));
    }

}
