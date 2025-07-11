/**
 * Generic Class: LOG <T>
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

public class LogUtils {

    public interface Logger {
        void func(String msg, Object... args);
        void info(String msg, Object... args);
        void debug(String msg, Object... args);
        void warn(String msg, Object... args);
        void error(String msg, Object... args);
        void success(String msg, Object... args);
    }

    public static Logger getLogger() {
        return new Logger() {
            private final String RESET = "\u001B[0m";
            private final String RED = "\u001B[31m";
            private final String GREEN = "\u001B[32m";
            private final String YELLOW = "\u001B[33m";
            private final String BLUE = "\u001B[34m";
            private final String GRAY = "\u001B[37m";
            private final String CYAN = "\u001B[36m";

            private void log(String label, String color, String msg, Object... args) {
                String formatted = format(msg, args);
                String caller = getCallerMethod();
                System.out.println(color + "[" + label + "]" + RESET + " (" + caller + ") " + formatted);
            }

            private String getCallerMethod() {
                StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                int CALLER_INDEX = 4;
                if (stack.length > CALLER_INDEX) {
                    StackTraceElement e = stack[CALLER_INDEX];
                    return e.getClassName().replaceAll("^.*\\.", "") + "." + e.getMethodName();
                }
                return "unknown";
            }

            private String format(String template, Object... args) {
                if (args == null || args.length == 0 || !template.contains("{}")) return template;
                StringBuilder sb = new StringBuilder();
                int argIndex = 0, i = 0;
                while (i < template.length()) {
                    if (i + 1 < template.length() && template.charAt(i) == '{' && template.charAt(i + 1) == '}') {
                        sb.append(argIndex < args.length ? args[argIndex++] : "{}");
                        i += 2;
                    } else {
                        sb.append(template.charAt(i++));
                    }
                }
                return sb.toString();
            }

            public void func(String msg, Object... args)   { log("FUNCTION", CYAN, msg, args); }
            public void info(String msg, Object... args)   { log("INFO", BLUE, msg, args); }
            public void debug(String msg, Object... args)  { log("DEBUG", GRAY, msg, args); }
            public void warn(String msg, Object... args)   { log("WARN", YELLOW, msg, args); }
            public void error(String msg, Object... args)  { log("ERROR", RED, msg, args); }
            public void success(String msg, Object... args){ log("SUCCESS", GREEN, msg, args); }
        };
    }
}
