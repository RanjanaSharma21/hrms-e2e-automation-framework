package ui.utils;

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

    public static String uniqueFirstName(String firstname) {
        if (firstname != null
                && !firstname.isEmpty()
                && !firstname.equalsIgnoreCase("Firstname")) {
            return firstname + UUID.randomUUID().toString().substring(0, 4);
        }
        return firstname;
    }

    public static String uniqueLastName(String lastname) {
        if (lastname != null
                && !lastname.isEmpty()
                && !lastname.equalsIgnoreCase("Lastname")) {
            return lastname + UUID.randomUUID().toString().substring(0, 4);
        }
        return lastname;
    }
}
