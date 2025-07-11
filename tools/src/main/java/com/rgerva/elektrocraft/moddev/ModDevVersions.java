/**
 *  Generic Class: ModDevVersions <T>
 *  A generic structure that works with type parameters.
 *  <p>
 *  Created by: D56V1OK
 *  On: 2025/jul.
 *  <p>
 *  GitHub: https://github.com/RGerva
 *  <p>
 *  Copyright (c) 2025 @RGerva. All Rights Reserved.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.moddev;

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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModDevVersions {

    private static final String MAVEN_METADATA_URL =
            "https://maven.neoforged.net/releases/net/neoforged/moddev/repositories/net.neoforged.moddev.repositories.gradle.plugin/maven-metadata.xml";
    private static final Path BUILD_GRADLE_PATH = Path.of("build.gradle");

    private final String localVersion;
    private final String releaseVersion;
    private final boolean outdated;

    public ModDevVersions() {
        this.localVersion = getLocalModDevVersion();
        this.releaseVersion = getModDevVersion();
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

    public void modDevCheck() {
        Main.LOGGER.func("ModDev Plugin Version Check");
        Main.LOGGER.info("Local version: {}", localVersion);
        Main.LOGGER.info("Latest Release available version: {}", releaseVersion);
        if (isOutdated()) {
            Main.LOGGER.warn("ModDev is outdated.");
            getUserConfirmation();
        } else {
            Main.LOGGER.success("ModDev is up to date.");
        }
    }

    private void getUserConfirmation() {
        Main.LOGGER.info("Do you want to update ModDev from {} to {} ? (y/N): ", getLocalVersion(), getReleaseVersion());
        String input = new Scanner(System.in).nextLine().trim().toLowerCase();

        if (input.equals("y") || input.equals("yes")) {
            updateBuildGradleVersion(getReleaseVersion());
        } else {
            Main.LOGGER.warn("Update canceled by user.");
        }
    }

    protected static void updateBuildGradleVersion(String newVersion) {
        try {
            List<String> lines = Files.readAllLines(BUILD_GRADLE_PATH);
            Pattern pattern = Pattern.compile("(id\\s+['\"]net\\.neoforged\\.moddev['\"]\\s+version\\s+['\"])([^'\"]+)(['\"])");

            for (int i = 0; i < lines.size(); i++) {
                Matcher matcher = pattern.matcher(lines.get(i));
                if (matcher.find()) {
                    lines.set(i, matcher.replaceFirst(matcher.group(1) + newVersion + matcher.group(3)));
                    break;
                }
            }

            String content = String.join("\n", lines) + "\n"; // ensure LF
            Files.writeString(BUILD_GRADLE_PATH, content, StandardOpenOption.TRUNCATE_EXISTING);

            Main.LOGGER.success("ModDev version updated in build.gradle to: {}", newVersion);
        } catch (IOException e) {
            Main.LOGGER.error("Failed to update build.gradle: {}", e.getMessage());
        }
    }

    protected boolean isOutdated(String current, String latest) {
        if (current == null || latest == null) return false;
        return !current.trim().equals(latest.trim());
    }

    protected String getLocalModDevVersion() {
        try {
            List<String> lines = Files.readAllLines(BUILD_GRADLE_PATH);
            Pattern pattern = Pattern.compile("id\\s+['\"]net\\.neoforged\\.moddev['\"]\\s+version\\s+['\"]([^'\"]+)['\"]");
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
            Main.LOGGER.error("ModDev plugin version not found in build.gradle");
            return null;
        } catch (IOException e) {
            Main.LOGGER.error("Could not read build.gradle: {}", e.getMessage());
            return null;
        }
    }

    protected String getModDevVersion() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(MAVEN_METADATA_URL))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() != 200) {
                Main.LOGGER.error("HTTP error while fetching ModDev metadata: {}", response.statusCode());
                return null;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(response.body());
            doc.normalize();

            NodeList nodes = doc.getElementsByTagName("release");
            return (nodes.getLength() > 0) ? nodes.item(0).getTextContent().trim() : null;

        } catch (Exception e) {
            Main.LOGGER.error("Failed to fetch ModDev version: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
