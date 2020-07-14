package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

public class ModeSelectActivity extends AppCompatActivity {
    String deck;
    String gamemode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //I don't understand why it's upset
        setContentView(R.layout.activity_mode_select);
        deck = getIntent().getStringExtra("DECK");
        /***************************************************************************************
         * What were going to do here is take a deck passed from deck select activity, pop a
         * gamemode on the intent, and send it on it's way.
         ***************************************************************************************/
    }

    public void startRegular(View view)
    {
        Intent intent = new Intent(this, PlayDeckActivity.class);
        intent.putExtra("DECK", deck);
        intent.putExtra("GAME_MODE", "regular");
        startActivity(intent);
        finish();
    }
    public void startReverse(View view)
    {
        Intent intent = new Intent(this, PlayDeckActivity.class);
        intent.putExtra("DECK", deck);
        intent.putExtra("GAME_MODE", "reverse");
        startActivity(intent);
        finish();
    }
}
