/**
 * Class: Main <T>
 * Created by: D56V1OK
 * On: 2025/jul.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rgerva.elektrocraft;

import com.rgerva.elektrocraft.gradle.GradleVersions;
import com.rgerva.elektrocraft.neoforge.NeoforgeVersions;
import com.rgerva.elektrocraft.neoforge.VersionField;
import com.rgerva.elektrocraft.utils.LogUtils;

import java.util.EnumMap;

import static com.rgerva.elektrocraft.utils.ConstantsURL.MODDEV_URL;
import static com.rgerva.elektrocraft.utils.ConstantsURL.NEOFORGE_URL;

public class Main {
    public static final LogUtils.Logger LOGGER = LogUtils.getLogger();

    public static void main(String[] args) {

        EnumMap<VersionField, String> neoforgeMetadata = NeoforgeVersions.getInfos(NEOFORGE_URL);

        LOGGER.info("ARTIFACT_ID: {}", neoforgeMetadata.get(VersionField.ARTIFACT_ID));
        LOGGER.info("GROUP_ID: {}", neoforgeMetadata.get(VersionField.GROUP_ID));
        LOGGER.info("LATEST: {}", neoforgeMetadata.get(VersionField.LATEST));
        LOGGER.info("RELEASE: {}", neoforgeMetadata.get(VersionField.RELEASE));

        EnumMap<VersionField, String> moddevMetadata = NeoforgeVersions.getInfos(MODDEV_URL);

        LOGGER.info("ARTIFACT_ID: {}", moddevMetadata.get(VersionField.ARTIFACT_ID));
        LOGGER.info("GROUP_ID: {}", moddevMetadata.get(VersionField.GROUP_ID));
        LOGGER.info("LATEST: {}", moddevMetadata.get(VersionField.LATEST));
        LOGGER.info("RELEASE: {}", moddevMetadata.get(VersionField.RELEASE));

        var gradleLocal = GradleVersions.fetchGradleWrapperVersion();
        LOGGER.info("Gradle Wrapper local: {}", gradleLocal.get(VersionField.GRADLE_VERSION));
        LOGGER.info("Gradle Version {}",GradleVersions.fetchLatestGradleVersion());
    }
}