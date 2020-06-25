package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;

public class DeckSelectActivity extends AppCompatActivity {

    public ListView listView;
    public Library localLibrary;
    public String[] deckNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_select);

        listView = findViewById(R.id.deckListView);
        localLibrary = new Library();

        Log.d("ALL", "Deck Select Started");
        try {
            localLibrary.loadMasterLibrary(getApplicationContext());
        } catch (IOException e) {
            Log.d("ALL", "Deck Select masterLibrary load error: " + e);
        }
        Log.d("ALL", "Master Library loaded");

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
}
