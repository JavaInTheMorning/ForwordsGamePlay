package com.example.danielfinlay.forwords.Model;

/**
 * Created by danielfinlay on 3/19/18.
 */

public class LanguageChoosen {


    private static LanguageChoosen instance = null;
    private String language = "";

    public static LanguageChoosen getInstance() {
        if (instance == null) {
            instance = new LanguageChoosen();
        }
        return instance;
    }

    private LanguageChoosen() {
        language = "english";
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }
}