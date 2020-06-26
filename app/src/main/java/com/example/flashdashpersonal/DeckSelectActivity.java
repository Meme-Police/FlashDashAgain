package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;

public class DeckSelectActivity extends AppCompatActivity {

    public ListView listView;
    public Library localLibrary;
    public String[] deckNames;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_select);

        listView = findViewById(R.id.deckListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                startActivityWithDeck(position);
            }
        });
        localLibrary = new Library();
        gson = new Gson();


        /**************************************************************************************
         * This loads all locally saved decks from the master file
         **************************************************************************************/

        Log.d("ALL", "Deck Select Started");
        try {
            localLibrary.loadMasterLibrary(getApplicationContext());
        } catch (IOException e) {
            Log.d("ALL", "Deck Select masterLibrary load error: " + e);
        }
        Log.d("ALL", "Master Library loaded");

        /**************************************************************************************
         * Now that we have loaded the master file, we know how big our string array has to be
         **************************************************************************************/
        deckNames = new String[localLibrary.deckList.size()];

        // loads each deck name into a string to use in the list view
        for (int i = 0; i < localLibrary.deckList.size(); i++)
        {
            deckNames[i] = localLibrary.deckList.get(i).deckName;
        }
        Log.d("ALL", "Parsed deck names");

        CustomAdapter arrayAdapter = new CustomAdapter(this, deckNames);
        listView.setAdapter(arrayAdapter);

        Log.d("ALL", "It is over, it is done.");

    }

    public void startActivityWithDeck(int index)
    {
        Intent originalActivity = this.getIntent();
        String intendedActivity = originalActivity.getStringExtra("nextActivity");
        Intent intent = new Intent (this, EditDeckActivity.class);
        intent.putExtra("DECK", gson.toJson(localLibrary.deckList.get(index)));
        startActivity(intent);
    }

}
