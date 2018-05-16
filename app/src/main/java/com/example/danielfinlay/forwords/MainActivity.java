package com.example.danielfinlay.forwords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.content.Intent;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by danielfinlay on 3/16/18.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_dash_board);



        //ImageButton btnPlayGame = findViewById(R.id.playButton);
        //Button btnLeaderBoard = findViewById(R.id.LeaderBoard);
        //Button btnStore = findViewById(R.id.Store);
        //Button btnSettings = findViewById(R.id.Settings);

        findViewById(R.id.playButton).setOnClickListener(new handlePlayButton());
        findViewById(R.id.Settings).setOnClickListener(new handleSettingsButton());
    }

    private void initUserSettings() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_settings");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class handlePlayButton implements View.OnClickListener {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_gamePlay.class);
                startActivity(intent);
            }

    }

    class handleSettingsButton implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        }

    }

}


