/**
 * Generic Class: ModUtils <T>
 * A generic structure that works with type parameters.
 * <p>
 * Created by: D56V1OK
 * On: 2025/jun.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.util;

import java.util.Locale;

public class ModUtils {
    public static class ModResistorUtil {
        public static int calculateResistance(ModItemProperties.ResistorColorCode first, ModItemProperties.ResistorColorCode second, ModItemProperties.ResistorColorCode multiplier) {
            int significantFigures = first.getDigit() * 10 + second.getDigit();
            int multiplierValue = (int) Math.pow(10, multiplier.getDigit());
            return significantFigures * multiplierValue;
        }

        private static final String[] RESISTANCE_PREFIXES = new String[]{
                "Ω", "kΩ", "MΩ", "GΩ", "TΩ", "PΩ"
        };

        public static String getResistanceWithPrefix(long resistance) {
            double value = resistance;
            int index = 0;
            while (value >= 1000 && index + 1 < RESISTANCE_PREFIXES.length) {
                value /= 1000.0;
                index++;
            }

            String formatted = (value % 1.0 == 0)
                    ? String.format(Locale.ENGLISH, "%d", (long) value)
                    : String.format(Locale.ENGLISH, "%.2f", value);
            return formatted + " " + RESISTANCE_PREFIXES[index];
        }

    }
}
