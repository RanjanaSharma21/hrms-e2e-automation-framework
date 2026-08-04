package utils.ui;

import java.util.UUID;

public class DataGenerator {

    public static String uniqueUsername(String prefix) {
        return prefix + System.currentTimeMillis() % 100000;
    }

    public static String shortUsername() {
        return "sh" + System.currentTimeMillis() % 100;
    }

    public static String uniqueEmail() {
        return UUID.randomUUID().toString().substring(0, 8) + "@syntax.com";
    }
}
