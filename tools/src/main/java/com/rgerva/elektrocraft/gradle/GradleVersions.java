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
import com.rgerva.elektrocraft.neoforge.VersionField;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.EnumMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rgerva.elektrocraft.utils.ConstantsURL.GRADLE_VERSION_URL;
import static com.rgerva.elektrocraft.utils.ConstantsURL.WRAPPER_PROPERTIES_PATH;

public class GradleVersions {

    public static EnumMap<VersionField, String> fetchGradleWrapperVersion() {
        EnumMap<VersionField, String> data = new EnumMap<>(VersionField.class);
        String localVersion = getLocalGradleWrapperVersion();
        data.put(VersionField.GRADLE_VERSION, localVersion);
        return data;
    }

    public static String getLocalGradleWrapperVersion() {
        Properties props = new Properties();
        try (InputStream input = Files.newInputStream(WRAPPER_PROPERTIES_PATH)) {
            props.load(input);
            String distributionUrl = props.getProperty("distributionUrl");
            return extractVersionFromUrl(distributionUrl);
        } catch (IOException e) {
            Main.LOGGER.error("Error to read gradle-wrapper.properties");
            throw new RuntimeException(e);
        }
    }

    private static String extractVersionFromUrl(String url) {
        if (url == null) return null;
        Pattern pattern = Pattern.compile("gradle-(.*?)-bin.zip");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String fetchLatestGradleVersion() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GRADLE_VERSION_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                Main.LOGGER.error("Error HTTP: {}", response.statusCode());
                return null;
            }
            Pattern pattern = Pattern.compile("\"version\"\\s*:\\s*\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(response.body());
            if (matcher.find()) {
                return matcher.group(1);
            }
            Main.LOGGER.error("Error: could not find JSON field");
            return null;
        } catch (Exception e) {
            Main.LOGGER.error("Error: Fail to search online Gradle version");
            throw new RuntimeException(e);
        }
    }
}
