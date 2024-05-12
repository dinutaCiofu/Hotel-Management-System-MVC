package org.example.utils;

public record CustomLocale(String language) {
    public static final CustomLocale SPANISH = new CustomLocale("es");
    public static final CustomLocale ENGLISH = new CustomLocale("en");
    public static final CustomLocale ROMANIAN = new CustomLocale("ro");

    public static String[] values() {
        return new String[]{SPANISH.getLanguage(), ENGLISH.getLanguage(), ROMANIAN.getLanguage()};
    }

    public String getLanguage() {
        return this.language;
    }
}
