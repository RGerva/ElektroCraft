/**
 * Generic Class: ConstantsURL <T>
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

package com.rgerva.elektrocraft.utils;

import java.nio.file.Path;

public class ConstantsURL {
    public static final String MODDEV_URL = "https://maven.neoforged.net/releases/net/neoforged/moddev/repositories/net.neoforged.moddev.repositories.gradle.plugin/maven-metadata.xml";
    public static final String NEOFORGE_URL = "https://maven.neoforged.net/releases/net/neoforged/neoforge/maven-metadata.xml";
    public static final String GRADLE_VERSION_URL = "https://services.gradle.org/versions/current";
    public static final Path WRAPPER_PROPERTIES_PATH = Path.of("gradle", "wrapper", "gradle-wrapper.properties");

    private ConstantsURL () {
        throw new UnsupportedOperationException("this class should not be instanced");
    }
}
