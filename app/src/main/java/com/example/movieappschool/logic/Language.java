package com.example.movieappschool.logic;

public enum Language {
    EN_US("en-US"),
    NL_NL("nl-NL");

    private String language;

    Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
