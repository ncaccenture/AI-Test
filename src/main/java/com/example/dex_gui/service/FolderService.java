package com.example.dex_gui.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FolderService {

    private static final String ROOT_FOLDER = "C:\\DEMO_DEX_USER_SHARE";

    public void createQuartetFolders(
            String compound,
            String study,
            String department,
            String partner) throws IOException {

        Path quartetPath = Path.of(
                ROOT_FOLDER,
                compound,
                study,
                department,
                partner
        );

        Path inboundPath = quartetPath.resolve("inbound");
        Path outboundPath = quartetPath.resolve("outbound");

        // Creates all parent folders as well
        Files.createDirectories(inboundPath);
        Files.createDirectories(outboundPath);

    }
}