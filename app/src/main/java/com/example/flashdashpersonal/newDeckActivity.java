package com.example.flashdashpersonal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class newDeckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);
    }

    /*************************************************************************************
     * ALRIGHT Here's the deal with how this works.
     *  Each of the three buttons will open the deck editor with the declared Deck below
     *  literallyMagic runs from Import Deck. It will convert the received text file to
     *      the Deck object before calling startDeckEditorActivity
     *  EditDeck will have to open some sort of list view with available decks which
     *      will then load the  selected deck into the editor via startDeckEditorActivity
     *  NewDeck will set deck to null before passing it to startDeckEditorActivity
     *
     *  What I need from someone else is to figure out how to pull a deck from a ListView
     *  containing all locally stored decks
     ************************************************************************************/
    Deck deck = new Deck();
    Gson gson = new Gson();

    public void deleteDeck(View view)
    {
        Intent intent = new Intent(this, DeckSelectActivity.class);
        intent.putExtra("nextActivity", "DeleteDeck");
        startActivity(intent);
    }

    public void saveDeck(View view)
    {
        Intent intent = new Intent(this, DeckSelectActivity.class);
        intent.putExtra("nextActivity", "Export");
        startActivity(intent);
    }

    // "Import Deck" button
    public void literallyMagic(View view)
    {
        // initialise new intent
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, 41);
    }

    // "Edit Deck" button
    public void EditDeck(View view)
    {

        Log.d("ALL", "Edit Deck Called");
         Intent intent = new Intent(this, DeckSelectActivity.class);
         intent.putExtra("nextActivity", "EditDeckActivity");

        Log.d("ALL", "About to start DeckSelect");
         startActivity(intent);

         // We don't need this since we will be starting this activity from the DeckSelectActivity
         // startDeckEditorActivity(deck);
    }

    // "New Deck" button
    public void NewDeck(View view)
    {
        // making sure deck is empty first
        deck = null;

        // sure I could just pass in null but I don't want to
        startDeckEditorActivity(deck/* I know its always null you butt*/);
    }

    /************************************************
     * All three button functions call this function
     ***********************************************/

    public void startDeckEditorActivity(Deck thisDeck)
    {
        // Create the intent for the Deck Editor Activity
        Intent intent = new Intent(this, EditDeckActivity.class);
        intent.putExtra("DECK", gson.toJson(thisDeck));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri deckURI;
        String deckContent = null;
        if (resultCode == Activity.RESULT_OK)
        {
            // if we use the SAF again we will need to add the following line
            // if (requestCode == 41)
            if (data != null)
            {
                deckURI = data.getData();

                try {
                    deckContent = readFileContent(deckURI);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            deck = gson.fromJson(deckContent, Deck.class);
            String test = deck.deck.get(0).side1;
            Log.d("THIS ACTIVITY", test);
            startDeckEditorActivity(deck);
        }
    }

    private String readFileContent(Uri deckURI) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(deckURI);

        // I don't know what the frell either of these are, but I have used them before and they work.
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(currentLine + "\n");
        }
        inputStream.close();
        return stringBuilder.toString();

    }
}
