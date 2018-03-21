package com.example.danielfinlay.forwords;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.danielfinlay.forwords.Model.LanguageChoosen;

public class Settings extends AppCompatActivity {

    RadioGroup chooseLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get language selected from radio button
        chooseLanguage = (RadioGroup) findViewById(R.id.chooseLanguage);

        chooseLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) chooseLanguage.findViewById(i);

                // Set the model's singleton class to store the language chosen by the radio button in user settings
                switch (rb.getId())
                {
                    case R.id.english:
                        LanguageChoosen.getInstance().setLanguage("english");
                        break;
                    case R.id.spanish:
                        LanguageChoosen.getInstance().setLanguage("spanish");
                        break;
                    case R.id.chinese:
                        LanguageChoosen.getInstance().setLanguage("chinese");
                        break;
                    case R.id.arabic:
                        LanguageChoosen.getInstance().setLanguage("arabic");
                        break;
                    case R.id.hungarian:
                        LanguageChoosen.getInstance().setLanguage("hungarian");
                        break;

                }

            }
        });

    }

}
