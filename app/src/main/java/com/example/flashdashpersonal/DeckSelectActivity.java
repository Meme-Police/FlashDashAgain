package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;

public class DeckSelectActivity extends AppCompatActivity {

    ListView listView = findViewById(R.id.deckListView);
    Library localLibrary = new Library();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_select);
        try {
            localLibrary.loadMasterLibrary(getApplicationContext());
        } catch (IOException e) {
            Log.d("ALL", "Deck Select masterLibrary load error: " + e);
        }

    }
}
