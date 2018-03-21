package com.example.danielfinlay.forwords.Model;

/**
 * Created by danielfinlay on 3/19/18.
 */

// Class Singleton model for storing which language was choosen in user settings by radio button
// Similiar class can be used for storing what category/level the user is on.
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
        // Set default language to english
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