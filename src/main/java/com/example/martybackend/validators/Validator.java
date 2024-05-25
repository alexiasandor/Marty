package com.example.martybackend.validators;

public class Validator {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
}
