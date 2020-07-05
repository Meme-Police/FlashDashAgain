package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

public class PlayDeckActivity extends AppCompatActivity {

    public Deck playDeck;
    public TextView cardView1;
    public TextView deckName;
    public Gson gson;
    boolean isFlipped = false;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);

        gson = new Gson();
        cardView1 = findViewById(R.id.deckCard);
        deckName = findViewById(R.id.deckLabel);
        Intent sentIntent = this.getIntent();
        playDeck = gson.fromJson(sentIntent.getStringExtra("DECK"), Deck.class);

        initCard1();
        deckName.setText("DECK");
    }

    public void initCard1(){
        cardView1.setText(playDeck.deck.get(position).side1);
    }

    public void initCard2() {
        cardView1.setText(playDeck.deck.get(position).side2);
    }

    public void flipCard() {
        if (!isFlipped) {
            initCard2();
            isFlipped = true;
        }
        else if (isFlipped) {
            initCard1();
            isFlipped = false;
        }
    }

    public void previousCard() {
        if (position > 0) {
            position -= 1;
            initCard1();
        }
        else if (position == 0) {
            position = position;
            initCard1();
        }
    }

    public void nextCard() {
        if (position >= 0 && position < playDeck.deck.size() - 1) {
            position += 1;
            initCard1();
        }
        else {
            position = position;
            initCard1();
        }
    }
}
