/**
 * Generic Class: GradleVersions <T>
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

package com.rgerva.elektrocraft.gradle;

import com.rgerva.elektrocraft.Main;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleVersions {

    private static final String GRADLE_VERSION_URL = "https://services.gradle.org/versions/current";
    private static final Path WRAPPER_PROPERTIES_PATH = Path.of("gradle", "wrapper", "gradle-wrapper.properties");

    private final String localVersion;
    private final String latestVersion;
    private final boolean outdated;

    public GradleVersions(){
        this.localVersion = getGradleWrapperLocalVersion();
        this.latestVersion = getLatestGradleVersion();
        this.outdated = isGradleOutdated(localVersion, latestVersion);
    }

    public String getLocalVersion() {
        return localVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public boolean isOutdated() {
        return outdated;
    }

    public void gradleCheck() {
        Main.LOGGER.func("Gradle Wrapper Version Check");
        Main.LOGGER.info("Current version: {}", localVersion);
        Main.LOGGER.info("Latest available version: {}", latestVersion);
        if(isOutdated()){
            Main.LOGGER.info("Gradle is outdated.");
            getUserConfirmation();
        }else{
            Main.LOGGER.success("Gradle is up to date.");
        }
    }

    private void getUserConfirmation() {
        Main.LOGGER.info("Do you want to update Gradle from {} to {} ? (y/N): ", getLocalVersion(), getLatestVersion());
        String input = new java.util.Scanner(System.in).nextLine().trim().toLowerCase();

        if (input.equals("y") || input.equals("yes")) {
            updateGradleWrapperVersion(getLatestVersion());
        }else {
            Main.LOGGER.warn("Update canceled by user.");
        }
    }

    protected static void updateGradleWrapperVersion(String newVersion){
        try {
            String newUrl = "https\\://services.gradle.org/distributions/gradle-" + newVersion + "-bin.zip";
            String content = "distributionUrl=" + newUrl + "\n";
            Files.writeString(WRAPPER_PROPERTIES_PATH, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            Main.LOGGER.success("gradle-wrapper.properties updated to: {}", newVersion);

        } catch (IOException e) {
            Main.LOGGER.error("Fail on update gradle-wrapper.properties {}", e.getMessage());
        }
    }

    protected static boolean isGradleOutdated(String current, String latest) {
        if (current == null || latest == null) return false;
        return !current.trim().equals(latest.trim());
    }

    protected static String getGradleWrapperLocalVersion(){
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(WRAPPER_PROPERTIES_PATH)){
            properties.load(inputStream);
            String getTag = properties.getProperty("distributionUrl");
            if(getTag == null){
                return null;
            }

            Pattern pattern = Pattern.compile("gradle-(.*?)-bin.zip");
            Matcher matcher = pattern.matcher(getTag);

            if(matcher.find()) {
                return matcher.group(1);
            }

            Main.LOGGER.error("Error: Could not find tag in gradle-wrapper.properties");
            return null;
        } catch (IOException e) {
            Main.LOGGER.error("Error: Could not read gradle-wrapper.properties {}", e.getMessage());
            return null;
        }
    }


    protected static String getLatestGradleVersion() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GRADLE_VERSION_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                Pattern pattern = Pattern.compile("\"version\"\\s*:\\s*\"([^\"]+)\"");
                Matcher matcher = pattern.matcher(response.body());

                if (matcher.find()) {
                    return matcher.group(1);
                }

                Main.LOGGER.error("Error: could not find tag on JSON");
                return null;
            }
            Main.LOGGER.error("Error HTTP: {}", response.statusCode());
            return null;

        } catch (Exception e) {
            Main.LOGGER.error("Error: Fail to search online Gradle version {}", e.getMessage());
            return null;
        }
    }
}
