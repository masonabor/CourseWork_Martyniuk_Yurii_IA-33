package com.coursework.coursework.ServiceLayer;

import java.util.Random;

public class PasswordGeneration {
    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        String symbols = "qwertyuiopasdfghjklzxcvbnm1234567890@!&^#$)(&?|";

        int length = random.nextInt(8) + 8;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(symbols.length());
            password.append(symbols.charAt(index));
        }
        return password.toString();
    }
}
