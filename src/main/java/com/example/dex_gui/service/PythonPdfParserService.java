package com.example.dex_gui.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dex_gui.dto.AutomationFormData;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PythonPdfParserService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AutomationFormData extractFormData(MultipartFile file) throws Exception {

        // --------------------------------------------------
        // 1. Create temporary PDF
        // --------------------------------------------------

        Path tempPdf = Files.createTempFile(
                "uploaded-form-",
                ".pdf"
        );

        try {

            file.transferTo(tempPdf.toFile());

            System.out.println("Temporary PDF:");
            System.out.println(tempPdf.toAbsolutePath());


            // --------------------------------------------------
            // 2. Python configuration
            // --------------------------------------------------

            String pythonCommand = "py";

            String pythonScript = new File("extract_form.py").getAbsolutePath();

            // --------------------------------------------------
            // 3. Execute Python
            // --------------------------------------------------

            ProcessBuilder processBuilder = new ProcessBuilder(
                    pythonCommand,
                    pythonScript,
                    tempPdf.toAbsolutePath().toString()
            );

            // Capture stdout + stderr
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();


            // --------------------------------------------------
            // 4. Read Python output
            // --------------------------------------------------

            StringBuilder output = new StringBuilder();

            try (
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            process.getInputStream()
                                    )
                            )
            ) {

                String line;

                while ((line = reader.readLine()) != null) {

                    output.append(line);
                }
            }


            // --------------------------------------------------
            // 5. Wait for Python
            // --------------------------------------------------

            int exitCode = process.waitFor();

            System.out.println("Python exit code: " + exitCode);

            // System.out.println("Python output:");
            // System.out.println(output);


            if (exitCode != 0) {
                throw new RuntimeException("Python parser failed: " + output);
            }


            // --------------------------------------------------
            // 6. JSON → Java DTO
            // --------------------------------------------------

            return objectMapper.readValue(
                    output.toString(),
                    AutomationFormData.class
            );

        } finally {

            // --------------------------------------------------
            // 7. Delete temporary PDF
            // --------------------------------------------------

            Files.deleteIfExists(tempPdf);
        }
    }
}