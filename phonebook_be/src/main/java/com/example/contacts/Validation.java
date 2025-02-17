package com.example.contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean validateText(String text) {
        if (text != null && !text.isBlank()) {
            String regex = "^[a-zA-Z0-9_ ]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        return true;
    }

    public static boolean validateNumber(String number) {
        if (number != null && !number.isBlank()) {
            String regex = "^\\d+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(number);
            return matcher.matches();
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        if (email != null && !email.isBlank()) {
            String regex = "^[a-zA-Z0-9._,@]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return true;
    }

    public static boolean validateActualText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return true;
        }
        return validateText(text);
    }

    public static boolean validateActualNumber(String number) {
        if (number == null) {
            return true;
        }
        return validateNumber(number);
    }

    public static boolean validateActualEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        return validateEmail(email);
    }
}
