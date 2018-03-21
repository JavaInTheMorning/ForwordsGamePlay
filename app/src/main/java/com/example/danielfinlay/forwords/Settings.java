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

        // Get Radio Button fields
        RadioButton eng = findViewById(R.id.english);
        RadioButton spn = findViewById(R.id.spanish);
        RadioButton chn = findViewById(R.id.chinese);
        RadioButton arb = findViewById(R.id.arabic);
        RadioButton hng = findViewById(R.id.hungarian);

        // Get the current language selected
        String lang = LanguageChoosen.getInstance().getLanguage();

        // Highlight current language
        switch (lang){
            case "english":
                eng.setChecked(true);
                break;
            case "spanish":
                spn.setChecked(true);
                break;
            case "chinese":
                chn.setChecked(true);
                break;
            case "arabic":
                arb.setChecked(true);
                break;
            case "hungarian":
                hng.setChecked(true);
                break;
            // If none selected yet default to english
            default:
                eng.setChecked(true);


        }

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
