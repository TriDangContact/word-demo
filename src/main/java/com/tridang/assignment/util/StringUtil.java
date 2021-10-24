package com.tridang.assignment.util;/*
 * @author Tri Dang
 */

public class StringUtil {
    public static String cleanInput(String input) {
        return input == null ? "" : input.trim().toLowerCase();
    }
}
