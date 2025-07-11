/**
 * Generic Class: NeoforgeChecker <T>
 * A generic structure that works with type parameters.
 * <p>
 * Created by: D56V1OK
 * On: 2025/jul.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.neoforge;

import com.rgerva.elektrocraft.Main;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class NeoforgeVersions {

    private static final String NEOFORGE_URL =
            "https://maven.neoforged.net/releases/net/neoforged/neoforge/maven-metadata.xml";
    private static final Path GRADLE_PROPERTIES_PATH = Path.of("gradle.properties");

    private final String localVersion;
    private final String releaseVersion;
    private final boolean outdated;

    public NeoforgeVersions() {
        this.localVersion = getLocalNeoVersion();
        this.releaseVersion = getInfos("release");
        this.outdated = isOutdated(localVersion, releaseVersion);
    }

    private String getLocalVersion() {
        return localVersion;
    }

    private String getReleaseVersion() {
        return releaseVersion;
    }

    private boolean isOutdated() {
        return outdated;
    }

    public void neoforgeCheck() {
        Main.LOGGER.func("NeoForge Version Check");
        Main.LOGGER.info("Local version: {}", localVersion);
        Main.LOGGER.info("Latest Release available version: {}", releaseVersion);
        if (isOutdated()) {
            Main.LOGGER.warn("NeoForge is outdated.");
            getUserConfirmation();
        } else {
            Main.LOGGER.success("NeoForge is up to date.");
        }
    }

    protected void getUserConfirmation() {
        Main.LOGGER.info("Do you want to update NeoForge from {} to {} ? (y/N): ", getLocalVersion(), getReleaseVersion());
        String input = new Scanner(System.in).nextLine().trim().toLowerCase();

        if (input.equals("y") || input.equals("yes")) {
            updateGradlePropertiesVersion(getReleaseVersion());
        } else {
            Main.LOGGER.warn("Update canceled by user.");
        }
    }

    protected static void updateGradlePropertiesVersion(String newVersion) {
        try {
            Path path = GRADLE_PROPERTIES_PATH;
            List<String> lines = Files.readAllLines(path);

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).trim().startsWith("neo_version=")) {
                    lines.set(i, "neo_version=" + newVersion);
                    break;
                }
            }
            String contentWithLf = String.join("\n", lines) + "\n";
            Files.writeString(path, contentWithLf, StandardOpenOption.TRUNCATE_EXISTING);

            Main.LOGGER.success("neo_version updated in gradle.properties to: {}", newVersion);

        } catch (IOException e) {
            Main.LOGGER.error("Failed to update gradle.properties: {}", e.getMessage());
        }
    }

    protected static boolean isOutdated(String current, String latest) {
        if (current == null || latest == null) return false;
        return !current.trim().equals(latest.trim());
    }

    protected static String getLocalNeoVersion() {
        Properties props = new Properties();
        try (InputStream input = Files.newInputStream(GRADLE_PROPERTIES_PATH)) {
            props.load(input);
            return props.getProperty("neo_version");
        } catch (IOException e) {
            Main.LOGGER.error("Could not read gradle.properties: {}", e.getMessage());
            return null;
        }
    }

    protected static String getInfos(String tagName){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(NEOFORGE_URL))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP error: " + response.statusCode());
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(response.body());
            doc.normalize();

            NodeList nodes = doc.getElementsByTagName(tagName);
            return (nodes.getLength() > 0) ? nodes.item(0).getTextContent().trim() : null;

        } catch (Exception e) {
            Main.LOGGER.error("Error {}", e);
            throw new RuntimeException(e);
        }
    }
}
