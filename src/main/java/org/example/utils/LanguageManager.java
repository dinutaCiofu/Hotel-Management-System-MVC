package org.example.utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class LanguageManager {
    // constructor privat!
    private static JSONObject currentLanguage;

    public static CustomLocale fromStringToLocale(String language) {
        return switch (language) {
            case "en" -> CustomLocale.ENGLISH;
            case "ro" -> CustomLocale.ROMANIAN;
            case "es" -> CustomLocale.SPANISH;
            default -> null;
        };
    }

    public static void loadLanguage(CustomLocale locale) {
        try {
            InputStream is = LanguageManager.class.getResourceAsStream("/lang/" + locale.getLanguage() + ".json");
            if (is == null) {
                is = LanguageManager.class.getResourceAsStream("/lang/en.json");
            }
            JSONTokener token;
            if (is != null) {
                token = new JSONTokener(is);
                currentLanguage = new JSONObject(token);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            currentLanguage = new JSONObject();
        }
    }

    public static String getString(String key) {
        return currentLanguage.optString(key, "Key not found: " + key);
    }
}