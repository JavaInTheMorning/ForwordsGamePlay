package com.example.danielfinlay.forwords;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        findViewById(R.id.playButton).setOnClickListener(new handleButton());
    }

    class handleButton implements View.OnClickListener {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_gamePlay.class);
                startActivity(intent);
            }

    }

}


