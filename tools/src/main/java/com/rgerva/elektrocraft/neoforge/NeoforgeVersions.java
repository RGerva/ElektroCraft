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
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.EnumMap;

public class NeoforgeVersions {

    public static EnumMap<VersionField, String> getInfos(String url){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP error: " + response.statusCode());
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(response.body());
            doc.normalize();

            EnumMap<VersionField, String> data = new EnumMap<>(VersionField.class);
            data.put(VersionField.GROUP_ID, getTextContent(doc, "groupId"));
            data.put(VersionField.ARTIFACT_ID, getTextContent(doc, "artifactId"));
            data.put(VersionField.LATEST, getTextContent(doc, "latest"));
            data.put(VersionField.RELEASE, getTextContent(doc, "release"));

            return data;

        } catch (Exception e) {
            Main.LOGGER.error("Error {}", e);
            throw new RuntimeException(e);
        }
    }

    private static String getTextContent(Document doc, String tag) {
        NodeList nodes = doc.getElementsByTagName(tag);
        return (nodes.getLength() > 0) ? nodes.item(0).getTextContent().trim() : null;
    }
}
