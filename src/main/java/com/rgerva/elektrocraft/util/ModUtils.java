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

import com.rgerva.elektrocraft.config.ModConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Locale;
import java.util.Map;
import java.util.OptionalDouble;

public class ModUtils {
    public static class ModUnits {
        public static int fePerVolt() {
            return ModConfig.fePerVolt;
        }

        public static long toFE(double volts) {
            return Math.round(volts * fePerVolt());
        }

        public static double toVolts(long fe) {
            return fe / (double) fePerVolt();
        }

        public static String formatVolts(double volts) {
            return String.format("%.2f V", volts);
        }

        public static String formatFE(long fe) {
            return fe + " FE";
        }
    }

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
                if(volts == 0.0){
                    index = 2;
                    break;
                }

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
                if(amperes == 0.0){
                    index = 3;
                    break;
                }

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

    public static class ModCapacitanceUtil {
        private static final String[] CAPACITANCE_PREFIXES = new String[]{
                "pF", "nF", "μF", "mF", "F", "kF", "MF", "GF"
        };

        private static final double[] THRESHOLDS = new double[]{
                1e-12, 1e-9, 1e-6, 1e-3, 1, 1e3, 1e6, 1e9
        };

        public static String getCapacitanceWithPrefix(double farads) {
            int index = 0;
            for (int i = THRESHOLDS.length - 1; i >= 0; i--) {
                if(farads == 0.0){
                    index = 4;
                    break;
                }

                if (farads >= THRESHOLDS[i]) {
                    index = i;
                    break;
                }
            }

            double adjusted = farads / THRESHOLDS[index];
            String formatted = (adjusted % 1.0 == 0)
                    ? String.format(Locale.ENGLISH, "%d", (long) adjusted)
                    : String.format(Locale.ENGLISH, "%.2f", adjusted);

            return formatted + " " + CAPACITANCE_PREFIXES[index];
        }

        public static final Map<Item, Double> DIELECTRIC_CONSTANTS = Map.ofEntries(
                Map.entry(Items.SLIME_BALL, 80.0),
                Map.entry(Items.PAPER, 3.0),
                Map.entry(Items.GLASS_PANE, 7.0),
                Map.entry(Items.GLOWSTONE_DUST, 100.0),
                Map.entry(Items.QUARTZ, 4.0),
                Map.entry(Items.REDSTONE, 120.0),
                Map.entry(Items.SNOWBALL, 1.0),
                Map.entry(Items.HONEYCOMB, 5.0),
                Map.entry(Items.AMETHYST_SHARD, 50.0)
        );

        public static OptionalDouble getDielectricConstant(Item item) {
            Double value = DIELECTRIC_CONSTANTS.get(item);
            return value != null ? OptionalDouble.of(value) : OptionalDouble.empty();
        }

    }
}
