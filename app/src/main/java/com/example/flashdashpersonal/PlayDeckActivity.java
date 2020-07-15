package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;

public class PlayDeckActivity extends AppCompatActivity {

    public Deck playDeck;
    public TextView cardView1;
    public TextView deckName;
    public Gson gson;
    String gameMode;
    boolean isFlipped = false;
    boolean finishedDeck;
    int position = 0;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);

        gson = new Gson();
        cardView1 = findViewById(R.id.deckCard);
        deckName = findViewById(R.id.deckLabel);
        Intent sentIntent = this.getIntent();
        playDeck = gson.fromJson(sentIntent.getStringExtra("DECK"), Deck.class);
        gameMode = sentIntent.getStringExtra("GAME_MODE");
        random = new Random();

        if (gameMode.equals("reverse"))
        {
            initCard2();
        }
        else if (gameMode.equals("random"))
        {
            random();
        }
        else
        {
            initCard1();
        }
        deckName.setText(playDeck.deckName);
    }

    public void initCard1(){ cardView1.setText(playDeck.deck.get(position).side1); }

    public void initCard2() {
        cardView1.setText(playDeck.deck.get(position).side2);
    }

    public void random()
    {
        if (random.nextBoolean())
        {
            initCard1();
            isFlipped = false;
        }
        else
        {
            initCard2();
            isFlipped = true;
        }
    }

    public void flipCard(View view) {
        if (!isFlipped) {
            initCard2();
            isFlipped = true;
        }
        else if (isFlipped) {
            initCard1();
            isFlipped = false;
        }
    }

    public void previousCard(View view) {
        if (position > 0) {
            position -= 1;
            initCard1();
            isFlipped = false;
        }
        else if (position == 0) {
            position = position;
            initCard1();
        }
    }

    public void nextCard(View view) {
        if (position >= 0 && position < playDeck.deck.size() - 1) {
            position += 1;
            if (gameMode.equals("reverse"))
            {
                initCard2();
                isFlipped = true;
            }
            else if (gameMode.equals("random"))
            {
                random();
            }
            else
            {
                initCard1();
                isFlipped = false;
            }


            if (position == playDeck.deck.size() - 1)
            {
                finishedDeck = true;
                return;
            }
        }
        else {
            position = position;
            initCard1();
        }

        if (finishedDeck) {
            Toast.makeText(this, "Desk finished", Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}
