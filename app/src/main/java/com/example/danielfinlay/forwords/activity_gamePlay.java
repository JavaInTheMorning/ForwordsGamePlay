package com.example.danielfinlay.forwords;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danielfinlay.forwords.Model.LanguageChoosen;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class activity_gamePlay extends AppCompatActivity {
    final int[] mProgressStatus = {0};
    int currentanswerButton = 0;
    int currentanswer = 0;
    int currentnotAnswer1 = 0;
    int currentnotAnswer2 = 0;
    int currentnotAnswer3 = 0;

    int nextanswerButton = 0;
    int nextanswer = 0;
    int nextnotAnswer1 = 0;
    int nextnotAnswer2 = 0;
    int nextnotAnswer3 = 0;
    boolean gameOver = false;
    boolean incorrect = false;
    String languageChoosen = LanguageChoosen.getInstance().getLanguage();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // Store english vocabulary linked to picture
        final ArrayList<String> english = new ArrayList<String>();
        // Store spanish vocabulary linked to picture
        final ArrayList<String> spanish = new ArrayList<String>();
        // Store chinese vocabulary linked to picture
        final ArrayList<String> chinese = new ArrayList<String>();
        // Store arabic vocabulary linked to picture
        final ArrayList<String> arabic = new ArrayList<String>();
        // Store hungarian vocabulary linked to picture
        final ArrayList<String> hungarian = new ArrayList<String>();


        // For gettting pictures from drawable folder
        final Map<String,Integer> imageList = new HashMap<String,Integer>();
        final R.drawable drawableResources = new R.drawable();
        final Class<R.drawable> c = R.drawable.class;
        final Field[] fields = c.getDeclaredFields();
        gameOver = false;

        // Read vocabulary from text file
        BufferedReader reader;

        try{
            final InputStream file = getAssets().open("vocabulary.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                // Split each line to english, corresponding spanish/chinese
                String[] s = line.split("_");
                english.add(s[0]);
                //Log.i("English.add(s[0]", english.get(english.indexOf(s[0])) + "At index: " + Integer.toString(english.indexOf(s[0])));
                spanish.add(s[1]);
                chinese.add(s[2]);
                arabic.add(s[3]);
                hungarian.add(s[4]);
                //Log.d("Parsed", s[0] + " " + s[1] + " " + s[2]);
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }

        // Traverse through all the fields in the drawable folder
        for (int i = 0, max = fields.length; i < max; i++) {
            final int resourceId;
            try {
                resourceId = fields[i].getInt(drawableResources);
            } catch (Exception e) {
                continue;
            }
            // If file endswith "_" then it's our picture
            if(fields[i].toString().endsWith("_")){
                //Log.i("Fields[i].toString()", fields[i].toString());
                String[] s = fields[i].toString().split("_");
                String engName = s[1];
                //Log.i("EngName: ", s[1]);
                int location = 0;
                // If picture's english name has a corresponding vocab in text file
                // then there are corresponding translations for every other language we add
                if(english.contains(engName)) {
                    location = english.indexOf(engName); // Save location of word for fetching other languages
                    //Log.i("eng.con("+engName+"):Location:", Integer.toString(location) );
                }
                //Log.i("String name", s[1]);
                if(languageChoosen.equalsIgnoreCase("english"))
                imageList.put(english.get(location), resourceId);
                else if(languageChoosen.equalsIgnoreCase("spanish"))
                    imageList.put(spanish.get(location), resourceId);
                else if(languageChoosen.equalsIgnoreCase("chinese"))
                    imageList.put(chinese.get(location), resourceId);
                else if(languageChoosen.equalsIgnoreCase("arabic"))
                    imageList.put(arabic.get(location), resourceId);
                else if(languageChoosen.equalsIgnoreCase("hungarian"))
                    imageList.put(hungarian.get(location),resourceId);
            }

        }

        final ArrayList<Integer> values = new ArrayList<Integer>(imageList.values());
        final ArrayList<String> keys = new ArrayList<String>(imageList.keySet());

        final Button btntopLeftImage = findViewById(R.id.topLeftImage);
        final Button btntopRightImage = findViewById(R.id.topRightImage);
        final Button btnbottomLeftImage = findViewById(R.id.bottomLeftImage);
        final Button btnbottomRightImage = findViewById(R.id.bottomRightImage);
        final ImageView image = findViewById(R.id.mainImage);


        // *****SET CURRENT ANSWER/PICTURES*****************
        Random rand = new Random();

        currentanswerButton = rand.nextInt(4);

        currentanswer = rand.nextInt(values.size());
        currentnotAnswer1 = rand.nextInt(values.size());
        currentnotAnswer2= rand.nextInt(values.size());
        currentnotAnswer3 = rand.nextInt(values.size());

        while ((currentanswer == currentnotAnswer1 || currentanswer == currentnotAnswer2 || currentanswer == currentnotAnswer3) || (currentnotAnswer1 == currentnotAnswer2 || currentnotAnswer1 == currentnotAnswer3) || (currentnotAnswer2== currentnotAnswer3)) {
            currentnotAnswer1 = rand.nextInt(values.size());
            currentnotAnswer2 = rand.nextInt(values.size());
            currentnotAnswer3 = rand.nextInt(values.size());
        }
        // Text 1
        if (currentanswerButton == 0) {
            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(currentanswer)));
            btntopLeftImage.setText(keys.get(currentanswer));
            btntopRightImage.setText(keys.get(currentnotAnswer1));
            btnbottomLeftImage.setText(keys.get(currentnotAnswer2));
            btnbottomRightImage.setText(keys.get(currentnotAnswer3));

        }

        // Text 2
        if (currentanswerButton== 1) {
            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(currentanswer)));
            btntopLeftImage.setText(keys.get(currentnotAnswer1));
            btntopRightImage.setText(keys.get(currentanswer));
            btnbottomLeftImage.setText(keys.get(currentnotAnswer2));
            btnbottomRightImage.setText(keys.get(currentnotAnswer3));
        }

        //Text 3
        if (currentanswerButton == 2) {
            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(currentanswer)));
            btntopLeftImage.setText(keys.get(currentnotAnswer1));
            btntopRightImage.setText(keys.get(currentnotAnswer2));
            btnbottomLeftImage.setText(keys.get(currentanswer));
            btnbottomRightImage.setText(keys.get(currentnotAnswer3));
        }

        //Text 4
        if (currentanswerButton == 3) {
            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(currentanswer)));
            btntopLeftImage.setText(keys.get(currentnotAnswer1));
            btntopRightImage.setText(keys.get(currentnotAnswer2));
            btnbottomLeftImage.setText(keys.get(currentnotAnswer3));
            btnbottomRightImage.setText(keys.get(currentanswer));
        }

        //loadBar();

        //*******************END SET CURRENT ANSWER*****************

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        final ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 0, 100);
        animation.setDuration(5000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
                proclaimGameOver();

            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        animation.start();

        btntopLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver) {
                    if (currentanswerButton != 0){
                        incorrect = true;
                        animation.end();}
                    else {
                        //mProgressStatus[0] = 0;
                        //loadBar();
                        //animation.cancel();
                        animation.start();

                        Random rand = new Random();


                        nextanswerButton = rand.nextInt(4);
                        currentanswerButton = nextanswerButton;

                        nextanswer = rand.nextInt(values.size());
                        nextnotAnswer1 = rand.nextInt(values.size());
                        nextnotAnswer2 = rand.nextInt(values.size());
                        nextnotAnswer3 = rand.nextInt(values.size());

                        while ((nextanswer == nextnotAnswer1 || nextanswer == nextnotAnswer2 || nextanswer == nextnotAnswer3 || nextanswer == currentanswer) || (nextnotAnswer1 == nextnotAnswer2 || nextnotAnswer1 == nextnotAnswer3) || (nextnotAnswer2 == nextnotAnswer3)) {
                            nextanswer = rand.nextInt(values.size());
                            nextnotAnswer1 = rand.nextInt(values.size());
                            nextnotAnswer2 = rand.nextInt(values.size());
                            nextnotAnswer3 = rand.nextInt(values.size());
                        }

                        currentanswer = nextanswer;
                        // Text 1
                        if (nextanswerButton == 0) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextanswer));
                            btntopRightImage.setText(keys.get(nextnotAnswer1));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer2));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));

                        }

                        // Text 2
                        if (nextanswerButton == 1) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextanswer));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer2));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));

                        }

                        //Text 3
                        if (nextanswerButton == 2) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextnotAnswer2));
                            btnbottomLeftImage.setText(keys.get(nextanswer));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));

                        }

                        //Text 4
                        if (nextanswerButton == 3) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextnotAnswer2));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer3));
                            btnbottomRightImage.setText(keys.get(nextanswer));
                        }
                    }
                }
            }
        });

        btntopRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver) {

                    if (currentanswerButton != 1){
                        incorrect = true;
                        animation.end();}
                    else{
                    //mProgressStatus[0] = 0;
                    //loadBar();
                        //animation.cancel();
                        animation.start();

                    Random rand = new Random();


                    int nextanswerButton = rand.nextInt(4);
                    currentanswerButton = nextanswerButton;

                    nextanswer = rand.nextInt(values.size());
                    nextnotAnswer1 = rand.nextInt(values.size());
                    nextnotAnswer2 = rand.nextInt(values.size());
                    nextnotAnswer3 = rand.nextInt(values.size());

                    while ((nextanswer == nextnotAnswer1 || nextanswer == nextnotAnswer2 || nextanswer == nextnotAnswer3 || nextanswer == currentanswer) || (nextnotAnswer1 == nextnotAnswer2 || nextnotAnswer1 == nextnotAnswer3) || (nextnotAnswer2 == nextnotAnswer3)) {
                        nextanswer = rand.nextInt(values.size());
                        nextnotAnswer1 = rand.nextInt(values.size());
                        nextnotAnswer2 = rand.nextInt(values.size());
                        nextnotAnswer3 = rand.nextInt(values.size());
                    }
                        currentanswer = nextanswer;
                    // Text 1
                    if (nextanswerButton == 0) {
                        image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                        btntopLeftImage.setText(keys.get(nextanswer));
                        btntopRightImage.setText(keys.get(nextnotAnswer1));
                        btnbottomLeftImage.setText(keys.get(nextnotAnswer2));
                        btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                    }

                    // Text 2
                    if (nextanswerButton == 1) {
                        image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                        btntopLeftImage.setText(keys.get(nextnotAnswer1));
                        btntopRightImage.setText(keys.get(nextanswer));
                        btnbottomLeftImage.setText(keys.get(nextnotAnswer2));
                        btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                    }

                    //Text 3
                    if (nextanswerButton == 2) {
                        image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                        btntopLeftImage.setText(keys.get(nextnotAnswer1));
                        btntopRightImage.setText(keys.get(nextnotAnswer2));
                        btnbottomLeftImage.setText(keys.get(nextanswer));
                        btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                    }

                    //Text 4
                    if (nextanswerButton == 3) {
                        image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                        btntopLeftImage.setText(keys.get(nextnotAnswer1));
                        btntopRightImage.setText(keys.get(nextnotAnswer2));
                        btnbottomLeftImage.setText(keys.get(nextnotAnswer3));
                        btnbottomRightImage.setText(keys.get(nextanswer));

                    }

                    }
                }

            }
        });

        btnbottomLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver) {
                    if (currentanswerButton != 2){
                        incorrect = true;
                        animation.end();}
                    else {
                        //mProgressStatus[0] = 0;
                        //loadBar();
                        //animation.cancel();
                        animation.start();

                        Random rand = new Random();


                        nextanswerButton = rand.nextInt(4);
                        currentanswerButton = nextanswerButton;

                        nextanswer = rand.nextInt(values.size());
                        nextnotAnswer1 = rand.nextInt(values.size());
                        nextnotAnswer2 = rand.nextInt(values.size());
                        nextnotAnswer3 = rand.nextInt(values.size());

                        while ((nextanswer == nextnotAnswer1 || nextanswer == nextnotAnswer2 || nextanswer == nextnotAnswer3 || nextanswer == currentanswer) || (nextnotAnswer1 == nextnotAnswer2 || nextnotAnswer1 == nextnotAnswer3) || (nextnotAnswer2 == nextnotAnswer3)) {
                            nextanswer = rand.nextInt(values.size());
                            nextnotAnswer1 = rand.nextInt(values.size());
                            nextnotAnswer2 = rand.nextInt(values.size());
                            nextnotAnswer3 = rand.nextInt(values.size());
                        }

                        currentanswer = nextanswer;
                        // Text 1
                        if (nextanswerButton == 0) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextanswer));
                            btntopRightImage.setText(keys.get(nextnotAnswer1));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer2));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                        }

                        // Text 2
                        if (nextanswerButton == 1) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextanswer));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer2));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                        }

                        //Text 3
                        if (nextanswerButton == 2) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextnotAnswer2));
                            btnbottomLeftImage.setText(keys.get(nextanswer));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                        }

                        //Text 4
                        if (nextanswerButton == 3) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextnotAnswer2));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer3));
                            btnbottomRightImage.setText(keys.get(nextanswer));
                        }
                    }
                }
            }
        });

        btnbottomRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver) {

                    if (currentanswerButton != 3){
                        incorrect = true;
                        animation.end();}
                    else {

                        //mProgressStatus[0] = 0;
                        //loadBar();
                        //animation.cancel();
                        animation.start();

                        Random rand = new Random();


                        nextanswerButton = rand.nextInt(4);
                        currentanswerButton = nextanswerButton;

                        nextanswer = rand.nextInt(values.size());
                        nextnotAnswer1 = rand.nextInt(values.size());
                        nextnotAnswer2 = rand.nextInt(values.size());
                        nextnotAnswer3 = rand.nextInt(values.size());

                        while ((nextanswer == nextnotAnswer1 || nextanswer == nextnotAnswer2 || nextanswer == nextnotAnswer3 || nextanswer == currentanswer) || (nextnotAnswer1 == nextnotAnswer2 || nextnotAnswer1 == nextnotAnswer3) || (nextnotAnswer2 == nextnotAnswer3)) {
                            nextanswer = rand.nextInt(values.size());
                            nextnotAnswer1 = rand.nextInt(values.size());
                            nextnotAnswer2 = rand.nextInt(values.size());
                            nextnotAnswer3 = rand.nextInt(values.size());
                        }

                        currentanswer = nextanswer;
                        // Text 1
                        if (nextanswerButton == 0) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextanswer));
                            btntopRightImage.setText(keys.get(nextnotAnswer1));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer2));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                        }

                        // Text 2
                        if (nextanswerButton == 1) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextanswer));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer2));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                        }

                        //Text 3
                        if (nextanswerButton == 2) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextnotAnswer2));
                            btnbottomLeftImage.setText(keys.get(nextanswer));
                            btnbottomRightImage.setText(keys.get(nextnotAnswer3));
                        }

                        //Text 4
                        if (nextanswerButton == 3) {
                            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(nextanswer)));
                            btntopLeftImage.setText(keys.get(nextnotAnswer1));
                            btntopRightImage.setText(keys.get(nextnotAnswer2));
                            btnbottomLeftImage.setText(keys.get(nextnotAnswer3));
                            btnbottomRightImage.setText(keys.get(nextanswer));
                        }
                    }
                }
            }
        });


    }






    private void proclaimGameOver()
    {
        final TextView mLoadingText = (TextView) findViewById(R.id.loadingCompleteTextView);
        mLoadingText.setText("Wasn't Set");

        if(incorrect)
        mLoadingText.setText("Incorrect");
        else if(!incorrect)
        mLoadingText.setText("Out Of Time");

        mLoadingText.setVisibility(View.VISIBLE);
        gameOver = true;
    }

}

