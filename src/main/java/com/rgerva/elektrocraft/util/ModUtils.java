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
    public static class ModLaws {
        public static class Ohm {
            public static float getVoltage(float Amp, int resistance) {
                return Amp * resistance;
            }

            public static float getAmp(float voltage, int resistance) {
                return voltage / resistance;
            }

            public static int getResistance(float voltage, float amp) {
                return (int) (voltage / amp);
            }

            public static int getPower(float voltage, float amp) {
                return (int) (voltage * amp);
            }
        }
    }

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

    public static class ModVoltageUtil {
        private static final String[] VOLTAGE_PREFIXES = new String[]{
                "μV", "mV", "V", "kV", "MV", "GV", "TV", "PV"
        };

        private static final double[] THRESHOLDS = new double[]{
                1e-6, 1e-3, 1, 1e3, 1e6, 1e9, 1e12, 1e15
        };

        public static String getVoltageWithPrefix(double volts) {
            int index = 0;
            for (int i = THRESHOLDS.length - 1; i >= 0; i--) {
                if (volts >= THRESHOLDS[i]) {
                    index = i;
                    break;
                }
            }

            double adjusted = volts / THRESHOLDS[index];
            String formatted = (adjusted % 1.0 == 0)
                    ? String.format(Locale.ENGLISH, "%d", (long) adjusted)
                    : String.format(Locale.ENGLISH, "%.2f", adjusted);

            return formatted + " " + VOLTAGE_PREFIXES[index];
        }
    }

    public static class ModCurrentUtil {
        private static final String[] CURRENT_PREFIXES = new String[]{
                "nA", "μA", "mA", "A", "kA", "MA", "GA", "TA", "PA"
        };

        private static final double[] THRESHOLDS = new double[]{
                1e-9, 1e-6, 1e-3, 1, 1e3, 1e6, 1e9, 1e12, 1e15
        };

        public static String getCurrentWithPrefix(double amperes) {
            int index = 0;
            for (int i = THRESHOLDS.length - 1; i >= 0; i--) {
                if (amperes >= THRESHOLDS[i]) {
                    index = i;
                    break;
                }
            }

            double adjusted = amperes / THRESHOLDS[index];
            String formatted = (adjusted % 1.0 == 0)
                    ? String.format(Locale.ENGLISH, "%d", (long) adjusted)
                    : String.format(Locale.ENGLISH, "%.2f", adjusted);

            return formatted + " " + CURRENT_PREFIXES[index];
        }
    }
}
