package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;

public class EditDeckActivity extends AppCompatActivity {

    Deck deck;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);
        EditText editDeckName = findViewById(R.id.editText);
        Gson gson = new Gson();
        Intent sentIntent = this.getIntent();
        // This gets the Deck object sent by the previous activity through a Json string
        Deck compareDeck = gson.fromJson(sentIntent.getStringExtra("DECK"), Deck.class);
        if (compareDeck != null)
            deck = compareDeck;
        else if (compareDeck == null)
            deck = new Deck();
        editDeckName.setText(deck.deckName);
    }
}
