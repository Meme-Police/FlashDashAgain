package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class EditDeckActivity extends AppCompatActivity {

    Deck deck;
    Gson gson = new Gson();
    String fileName = "localDeckLibrary.txt";
    EditText editDeckName;
    EditText editSide1;
    EditText editSide2;
    Integer cardNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);
        editDeckName = findViewById(R.id.deckName);
        editSide1 = findViewById(R.id.sideOne);
        editSide2 = findViewById(R.id.sideTwo);
        Intent sentIntent = this.getIntent();
        // This gets the Deck object sent by the previous activity through a Json string
        Deck compareDeck = gson.fromJson(sentIntent.getStringExtra("DECK"), Deck.class);
        if (compareDeck != null)
            deck = compareDeck;
        else if (compareDeck == null)
            deck = new Deck();


        // basically my idea here is to
        if (deck.deck.size() == 0)
        {
            cardNumber = -1;
            loadCard(cardNumber);
        }
        else if (deck.deck.size() > 0)
        {
            cardNumber = 0;
            loadCard(cardNumber);
        }
    }

    public void loadCard(int i)
    {
        if (cardNumber >= 0) {
            editDeckName.setText(deck.deckName);
            editSide1.setText(deck.deck.get(i).side1);
            editSide2.setText(deck.deck.get(i).side2);
        }
        else if (cardNumber == -1)
        {
          cardNumber = 0;
          deck.deck.add(new Card());
        }
    }

    public void saveCard(int i)
    {
        Card tempCard = new Card();
        deck.deckName = editDeckName.getText().toString();
        tempCard.side1 = editSide1.getText().toString();
        tempCard.side2 = editSide2.getText().toString();
        deck.deck.set(i, tempCard);
    }


    public void saveDeck(View view) throws IOException
    {
        saveCard(cardNumber);
        Library masterLibrary = new Library();
        masterLibrary.loadMasterLibrary(getApplicationContext());

        if (masterLibrary == null)
        {
            masterLibrary = new Library();
        }
        masterLibrary.deckList.add(deck);
        String newMasterLibraryString = gson.toJson(masterLibrary);
        Log.d("THIS ACTIVITY", masterLibrary.deckList.get(masterLibrary.deckList.size() - 1).deckName);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE));
        outputStreamWriter.write(newMasterLibraryString);
        outputStreamWriter.close();
    }
}
