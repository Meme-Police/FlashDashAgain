package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
    Spinner spinner;
    Integer cardNumber;
    public String[] cardNames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);
        editDeckName = findViewById(R.id.deckName);
        editSide1 = findViewById(R.id.sideOne);
        editSide2 = findViewById(R.id.sideTwo);
        spinner = findViewById(R.id.cardList);
        Log.d("ALL", "Spinner at: " + ((Integer) spinner.getSelectedItemPosition()).toString());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                spinnerSelect(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // do nothing lol
            }
        });
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

        loadSpinner();
    }

    public void loadCard(int i)
    {
        Log.d("ALL", "Card number is " + cardNumber);
        if (cardNumber >= 0) {
            editDeckName.setText(deck.deckName);
            editSide1.setText(deck.deck.get(i).side1);
            editSide2.setText(deck.deck.get(i).side2);
        }
        else if (cardNumber == -1)
        {
          deck.deck.add(new Card());
          cardNumber = 0;
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

    public void addCard(View view)
    {
        saveCard(cardNumber);
        deck.deck.add(new Card());
        cardNumber = deck.deck.size() -1;
        loadSpinner();
        Log.d("ALL", "Woahhh ohh, were half way there");
        spinner.setSelection(deck.deck.size() -1);
        loadCard(cardNumber);
    }

    public void spinnerSelect(int i)
    {
        saveCard(cardNumber);
        cardNumber = i;
        loadCard(cardNumber);
    }

    public void deleteCard(View view)
    {
        // if you delete the last card it might crash the program, but I don't want to deal with it right now
        Log.d("ALL", "Deleting: " + cardNumber.toString());
        deck.deck.remove((int)cardNumber);
        loadSpinner();
        loadCard(cardNumber);
    }

    public void loadSpinner()
    {
        cardNames = new String[deck.deck.size()];

        // loads each card number into a string to use in the list view
        for (int i = 0; i < deck.deck.size(); i++)
        {
            // don't ask
            Integer j = i + 1;
            cardNames[i] = j.toString();
        }
        Log.d("ALL", "Parsed card names");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cardNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    /**CustomAdapter arrayAdapter = new CustomAdapter(this, cardNames);
    spinner.setAdapter(arrayAdapter);
     **/



    public void saveDeck/*I can do whatever I want*/(View view) throws IOException
    {
        saveCard(cardNumber);
        Boolean deckAlreadyExist = false;
        Integer indexOfExistingDeck = 0;
        Library masterLibrary = new Library();
        masterLibrary.loadMasterLibrary(getApplicationContext());

        if (masterLibrary == null)
        {
            masterLibrary = new Library();
        }

        for (int i = 0; i < masterLibrary.deckList.size(); i++)
        {
            if (masterLibrary.deckList.get(i).deckName.equals(deck.deckName))
            {
                deckAlreadyExist = true;
                indexOfExistingDeck = i;
            }
        }

        if(!deckAlreadyExist) {
            masterLibrary.deckList.add(deck);
        }
        else
        {
            masterLibrary.deckList.set(indexOfExistingDeck, deck);
        }

        String newMasterLibraryString = gson.toJson(masterLibrary);
        Log.d("THIS ACTIVITY", masterLibrary.deckList.get(masterLibrary.deckList.size() - 1).deckName);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE));
        outputStreamWriter.write(newMasterLibraryString);
        outputStreamWriter.close();
        finish();
    }
}
