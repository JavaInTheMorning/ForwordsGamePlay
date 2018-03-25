package com.example.danielfinlay.forwords;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.danielfinlay.forwords.Model.LanguageChoosen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class activity_gamePlay extends AppCompatActivity {

    // Declare global variables so they're accessible everywhere
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

    // For storing which language radio button has currently been selected
    String languageChoosen = LanguageChoosen.getInstance().getLanguage();

    //boolean isMoving = true;

    ArrayList<Integer> values = null;
    ArrayList<String> keys = null;

    // As more languages are added, declare more arraylists here
    ArrayList<String> english = null;
    ArrayList<String> spanish = null;
    ArrayList<String> chinese = null;
    ArrayList<String> arabic = null;
    ArrayList<String> hungarian = null;

    ObjectAnimator animation = null;
    Button btntopLeftImage = null;
    Button btntopRightImage = null;
    Button btnbottomLeftImage = null;
    Button btnbottomRightImage = null;
    Button btnReset = null;
    ImageView image = null;
    TextView mLoadingText = null;
    Random rand;

    //float topLx = 0;
    //float topLy = 0;

    //float topRx = 0;
    //float topRy = 0;
    //float botLx = 0;
    //float botLy = 0;

    //float botRx = 0;
    //float botRy = 0;

    // Execute this code when screen loads
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize super class
        super.onCreate(savedInstanceState);

        // Set context to proper .xml layout
        setContentView(R.layout.activity_game_play);

        // Set GUI objects equal to their .xml elements
        btntopLeftImage = findViewById(R.id.topLeftImage);
        btntopRightImage = findViewById(R.id.topRightImage);
        btnbottomLeftImage = findViewById(R.id.bottomLeftImage);
        btnbottomRightImage = findViewById(R.id.bottomRightImage);
        btnReset = findViewById(R.id.resetButton);
        image = findViewById(R.id.mainImage);
        mLoadingText = (TextView) findViewById(R.id.loadingCompleteTextView);

        /*topLx = btntopLeftImage.getX();
        topLy = btntopLeftImage.getY();

        topRx = btntopRightImage.getX();
        topRy = btntopRightImage.getY();

        botLx = btnbottomLeftImage.getX();
        botLy = btnbottomLeftImage.getY();

        botRx = btnbottomRightImage.getX();
        botRy = btnbottomRightImage.getY();
        */

        // For Storing english vocabulary linked to picture
        english = new ArrayList<String>();

        // For Storing spanish vocabulary linked to picture
        spanish = new ArrayList<String>();

        // For Storing chinese vocabulary linked to picture
        chinese = new ArrayList<String>();

        // For Storing arabic vocabulary linked to picture
        arabic = new ArrayList<String>();

        // For Storing hungarian vocabulary linked to picture
        hungarian = new ArrayList<String>();

        // PUT MORE LANGUAGES HERE
        // TODO: WRITE new languages here

        // Variables for gettting pictures from drawable folder
        final Map<String,Integer> imageList = new HashMap<String,Integer>();
        final R.drawable drawableResources = new R.drawable();
        final Class<R.drawable> c = R.drawable.class;
        final Field[] fields = c.getDeclaredFields();


        // On create initialize Gameover to false
        gameOver = false;

        //isMoving = true;

        // ***********************Read vocabulary from text file*******************************
        BufferedReader reader;

        try{
            final InputStream file = getAssets().open("vocabulary.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                // Split each line to english, corresponding spanish/chinese
                String[] s = line.split("_");

                // Store eng/spn/chn/arab/hung/etc vocab into corresponding arraylists in text file vocabulary.txt found in assets folder
                english.add(s[0]);
                spanish.add(s[1]);
                chinese.add(s[2]);
                arabic.add(s[3]);
                hungarian.add(s[4]);
                // PUT MORE LANGUAGES HERE****
                // TODO: Put more languages here

                //Read the next line until end of file
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }

        //***********************END READ VOCABULARY FROM TEXT FILE*********************************



        //************************TRAVERSE THROUGH ALL OF THE FILES IN THE DRAWABLE FOLDER AND STORE ONLY THE PICTURES WITH THEIR CORRECT TRANSLATIONS IN A HASHMAP******************
        for (int i = 0, max = fields.length; i < max; i++) {
            // For storing the corresponding integer value that matches the pictures in the drawable folder
            final int resourceId;

            try {
                resourceId = fields[i].getInt(drawableResources);
            } catch (Exception e) {
                continue;
            }

            // If file endswith "_" then it's our picture
            if(fields[i].toString().endsWith("_")){

                // Split into:
                // s[0] = category
                // s[1] = english word
                // s[2] = spanish(Unecessary now since vocab is stored in text file)
                String[] s = fields[i].toString().split("_");

                // Get english translation from file name
                String engName = s[1];

                // int for storing location picture's eng translation is found in text file arrayLists
                int location = 0;

                // If picture's english name has a corresponding vocab in text file
                // then there are corresponding translations for every other language we add
                if(english.contains(engName)) {
                    // Save location of word for fetching other languages
                    location = english.indexOf(engName);

                }

                // Now use the LanguageChoosen singleton class, which has stored the radio button of the target language desired
                //Store the translation of the language desired with matchin picture
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
                // TODO: Put more else if statements here to add more languages
                // Final "To do" to add another language(Besides in LanguageChoosen.java and activity_settings.xml)
            }

        }

        // ***********************END TRAVERSE THROUGH ALL OF THE FILES IN THE DRAWABLE FOLDER*******************************



        // Initialize the ResourceID values from image list into an arraylist called Values for easier access
        values = new ArrayList<Integer>(imageList.values());
        // Initialize corresponding String name in keys Arraylist
        keys = new ArrayList<String>(imageList.keySet());

        // Initialize The starting picture and text options
        initializeAnswers();


        // CODE FOR PROGRESS BAR START*********************************
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        animation = ObjectAnimator.ofInt(pb, "progress", 0, 100);

        // Let clock run for 5 seconds(5000 milliseconds)
        animation.setDuration(4000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //If it's not a game over then the pieces should be moving
                /*if(isMoving) {
                    // So create a timer every reset to move the buttons around at a consistent pace
                    final Timer t = new Timer();
                    t.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            // Call move buttons every x seconds
                            moveButtons();
                            // If gameover has occured/they shouldn't be moving, delete the timer so a new one can be created
                            if(!isMoving)
                            t.cancel();
                        }
                    }, 0, 100);//put here time 1000 milliseconds=1 second
                }*/
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Declare game over when the countdown is complete
                proclaimGameOver();


            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // Do something if the animation is cancelled
            }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });



        // Start the animation as soon as the GamePlay xml screen loads
        animation.start();

        // CODE FOR PROGRESS BAR END*********************************




        // When btnReset is clicked
        btnReset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                // When the game is reset, it is no longer a gameover
                gameOver = false;

                // On reset, the answer hasn't been selected yet so can't be incorrect yet
                incorrect = false;

                // When the game is reset, make the reset button invisible
                btnReset.setVisibility(View.INVISIBLE);

                // Re-initialize the answers when the game is reset
                initializeAnswers();

                // Make sure that all of the buttons are default round buttons, instead of correct answer buttons on reset
                btntopLeftImage.setBackgroundResource(R.drawable.round_button);
                btntopRightImage.setBackgroundResource(R.drawable.round_button);
                btnbottomLeftImage.setBackgroundResource(R.drawable.round_button);
                btnbottomRightImage.setBackgroundResource(R.drawable.round_button);

                // Set the "GAME OVER"/"INCORRECT" text below the progress bar to be invisible
                mLoadingText.setVisibility(View.INVISIBLE);

                // make the buttons resume motion
                //isMoving = true;


                // Start the animation back up
                animation.start();

            }

        });

        btntopLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver) {
                    // CurrentanswerButton has stored which button number has the correct answer
                    // btntopLeftImage == 0
                    // btntopRightImage == 1
                    // btnbottomLeftImage == 2
                    // btnbottomRightImage == 3
                    // So if currentAnswerButton != 0, & they selected btntopleftImage -> set game over(because they selected the wrong button)
                    if (currentanswerButton != 0){

                        // Then since the wrong answer was choosen, change the correct answer text box to the round_button_answer to highlight correct answer(declared in drawable folder,color is blue...lol)
                        if(currentanswerButton == 1)
                            btntopRightImage.setBackgroundResource(R.drawable.round_button_answer);
                        else if(currentanswerButton == 2)
                            btnbottomLeftImage.setBackgroundResource(R.drawable.round_button_answer);
                        else if(currentanswerButton == 3)
                            btnbottomRightImage.setBackgroundResource(R.drawable.round_button_answer);

                        // Declare that the incorrect option was selected
                        incorrect = true;

                        shakeButton(btntopLeftImage);

                        // End the animation
                        // This method calls ProclaimGameOver() -> public void onAnimationEnd(Animator animator) listed above^
                        animation.end();
                    }

                    // Otherwise continue gameplay
                    else {
                        // Set the new options/picture for the next question if it's not a game over & the correct button was selected
                        // btntopLeftImage == 0
                        // btntopRightImage == 1
                        // btnbottomLeftImage == 2
                        // btnbottomRightImage == 3
                        setOptions();
                    }
                }
            }
        });

        // SAME COMMENTS/Conept AS btnTopLeftImage
        btntopRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver) {

                    if (currentanswerButton != 1){
                        if(currentanswerButton == 0)
                            btntopLeftImage.setBackgroundResource(R.drawable.round_button_answer);
                        else if(currentanswerButton == 2)
                            btnbottomLeftImage.setBackgroundResource(R.drawable.round_button_answer);
                        else if(currentanswerButton == 3)
                            btnbottomRightImage.setBackgroundResource(R.drawable.round_button_answer);
                        incorrect = true;

                        shakeButton(btntopRightImage);

                        animation.end();}
                    else{
                        // Set the new options/picture for the next question if it's not a game over & the correct button was selected
                        // btntopLeftImage == 0
                        // btntopRightImage == 1
                        // btnbottomLeftImage == 2
                        // btnbottomRightImage == 3
                        setOptions();
                    }
                }

            }
        });

        // SAME COMMENTS/Concept AS btnTopLeftImage
        btnbottomLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver) {
                    if (currentanswerButton != 2){
                        if(currentanswerButton == 0)
                            btntopLeftImage.setBackgroundResource(R.drawable.round_button_answer);
                        else if(currentanswerButton == 1)
                            btntopRightImage.setBackgroundResource(R.drawable.round_button_answer);
                        else if(currentanswerButton == 3)
                            btnbottomRightImage.setBackgroundResource(R.drawable.round_button_answer);
                        incorrect = true;

                        shakeButton(btnbottomLeftImage);

                        animation.end();}
                    else {
                        // Set the new options/picture for the next question if it's not a game over & the correct button was selected
                        // btntopLeftImage == 0
                        // btntopRightImage == 1
                        // btnbottomLeftImage == 2
                        // btnbottomRightImage == 3
                        setOptions();
                    }
                }
            }
        });

        // SAME COMMENTS/Concept AS btnTopLeftImage
        btnbottomRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver) {

                    if (currentanswerButton != 3){
                        if(currentanswerButton == 0)
                            btntopLeftImage.setBackgroundResource(R.drawable.round_button_answer);
                        else if(currentanswerButton == 1)
                            btntopRightImage.setBackgroundResource(R.drawable.round_button_answer);
                        else if(currentanswerButton == 2)
                            btnbottomLeftImage.setBackgroundResource(R.drawable.round_button_answer);
                        incorrect = true;

                        shakeButton(btnbottomRightImage);

                        animation.end();}
                    else
                    {
                        // Set the new options/picture for the next question if it's not a game over & the correct button was selected
                        // btntopLeftImage == 0
                        // btntopRightImage == 1
                        // btnbottomLeftImage == 2
                        // btnbottomRightImage == 3
                        setOptions();
                    }
                }
            }
        });


    }


    // Method for initializing answers, called in onCreate() & Reset
    private void initializeAnswers(){
        // Declare a random variable for randomly changing between pictures & random text options
        rand = new Random();

        // Set currentanswerbutton to be between 0-3, that way the button storing the correct answer is randomized, but we have 0-3 directly map to each button for deciding which button is correct
        // btntopLeftImage == 0
        // btntopRightImage == 1
        // btnbottomLeftImage == 2
        // btnbottomRightImage == 3
        currentanswerButton = rand.nextInt(4);

        // Randomly select a picture's abstracted integer value from the values array and set it to the current answer, then get 3 other values that are incorrect
        currentanswer = rand.nextInt(values.size());
        currentnotAnswer1 = rand.nextInt(values.size());
        currentnotAnswer2= rand.nextInt(values.size());
        currentnotAnswer3 = rand.nextInt(values.size());

        // Keep picking new options until none of them are the same to avoid duplicate options
        while ((currentanswer == currentnotAnswer1 || currentanswer == currentnotAnswer2 || currentanswer == currentnotAnswer3) || (currentnotAnswer1 == currentnotAnswer2 || currentnotAnswer1 == currentnotAnswer3) || (currentnotAnswer2== currentnotAnswer3)) {
            currentnotAnswer1 = rand.nextInt(values.size());
            currentnotAnswer2 = rand.nextInt(values.size());
            currentnotAnswer3 = rand.nextInt(values.size());
        }

        // Now Determine which button has the correct answer stored in it from the randomly generated int "currentanswerButton"
        // Button 1
        if (currentanswerButton == 0) {
            // Once determined set the center of the screen's image background to be the picture that is stored in the values arraylist at index "currentanswer"
            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(currentanswer)));
            // Then set the corresponding button's text to be the correct/or incorrect options accordingly
            btntopLeftImage.setText(keys.get(currentanswer));
            btntopRightImage.setText(keys.get(currentnotAnswer1));
            btnbottomLeftImage.setText(keys.get(currentnotAnswer2));
            btnbottomRightImage.setText(keys.get(currentnotAnswer3));

        }
        // Same concept as Button 1
        // Button 2
        if (currentanswerButton== 1) {
            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(currentanswer)));
            btntopLeftImage.setText(keys.get(currentnotAnswer1));
            btntopRightImage.setText(keys.get(currentanswer));
            btnbottomLeftImage.setText(keys.get(currentnotAnswer2));
            btnbottomRightImage.setText(keys.get(currentnotAnswer3));
        }

        // Same concept as Button 1
        // Button 3
        if (currentanswerButton == 2) {
            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(currentanswer)));
            btntopLeftImage.setText(keys.get(currentnotAnswer1));
            btntopRightImage.setText(keys.get(currentnotAnswer2));
            btnbottomLeftImage.setText(keys.get(currentanswer));
            btnbottomRightImage.setText(keys.get(currentnotAnswer3));
        }

        // Same concept as Button 1
        // Button 4
        if (currentanswerButton == 3) {
            image.setBackground(ContextCompat.getDrawable(activity_gamePlay.this, values.get(currentanswer)));
            btntopLeftImage.setText(keys.get(currentnotAnswer1));
            btntopRightImage.setText(keys.get(currentnotAnswer2));
            btnbottomLeftImage.setText(keys.get(currentnotAnswer3));
            btnbottomRightImage.setText(keys.get(currentanswer));
        }

    }

    // Same concept as initializeAnswers but used for getting the nextAnswer ready, also calls animation.start to reset the timer(since the correct answer was selected)
    private void setOptions()
    {
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


    public void shakeButton(View v)
    {

        Animation shake = AnimationUtils.loadAnimation(activity_gamePlay.this,R.anim.shake);
        v.startAnimation(shake);
    }

    /*public void moveButtons(){
        if(isMoving) {
            /*float topLx = btntopLeftImage.getX();
            float topLy = btntopLeftImage.getY();

            float topRx = btntopRightImage.getX();
            float topRy = btntopRightImage.getY();

            float botLx = btnbottomLeftImage.getX();
            float botLy = btnbottomLeftImage.getY();

            float botRx = btnbottomRightImage.getX();
            float botRy = btnbottomRightImage.getY();

            btntopLeftImage.setX(botRx);
            btntopLeftImage.setY(botRy);

            btnbottomRightImage.setX(topLx);
            btnbottomRightImage.setY(topLy);

            btnbottomLeftImage.setX(topRx);
            btnbottomLeftImage.setY(topRy);

            btntopRightImage.setX(botLx);
            btntopRightImage.setY(botLy);*/
        /*
            moveButton(btntopLeftImage);
            moveButton(btntopRightImage);
            moveButton(btnbottomLeftImage);
            moveButton(btnbottomRightImage);

        }


       public void moveButton(View v){
        v.setY(v.getY() - (float)1);
       }
    */


    // Called in animation.end()
    // Set's the text to either "Out of time" or "Incorrect" depending on which one occurred
    private void proclaimGameOver()
    {
        // Stop buttons from moving
        //isMoving = false;

        if(incorrect){
            mLoadingText.setText("Incorrect");

            // Once proper text determined make the text visible
            mLoadingText.setVisibility(View.VISIBLE);

            // since it's a game over make the reset button visible, so it's Onclick method can be called
            btnReset.setVisibility(View.VISIBLE);

        }

        else if(!incorrect){
            mLoadingText.setText("Out Of Time");

            // Once proper text determined make the text visible
            mLoadingText.setVisibility(View.VISIBLE);
            // since it's a game over make the reset button visible, so it's Onclick method can be called
            btnReset.setVisibility(View.VISIBLE);

            // Shake all the buttons except for correct answer on gameover if time runs out
            Animation shake = AnimationUtils.loadAnimation(activity_gamePlay.this,R.anim.shake);
            if(currentanswerButton == 0)
                btntopLeftImage.setBackgroundResource(R.drawable.round_button_answer);
            if(currentanswerButton == 1)
                btntopRightImage.setBackgroundResource(R.drawable.round_button_answer);
            if(currentanswerButton == 2)
                btnbottomLeftImage.setBackgroundResource(R.drawable.round_button_answer);
            if(currentanswerButton == 3)
                btnbottomRightImage.setBackgroundResource(R.drawable.round_button_answer);

            if(currentanswerButton != 0)
                btntopLeftImage.startAnimation(shake);
            if(currentanswerButton != 1)
                btntopRightImage.startAnimation(shake);
            if(currentanswerButton != 2)
                btnbottomLeftImage.startAnimation(shake);
            if(currentanswerButton != 3)
                btnbottomRightImage.startAnimation(shake);

        }

        // Set gameOver to true so none of the buttons will do anything until reset button is clicked(and its set back to false)
        gameOver = true;
    }

}

