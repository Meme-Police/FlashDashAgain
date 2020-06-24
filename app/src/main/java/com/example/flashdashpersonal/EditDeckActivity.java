package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

public class EditDeckActivity extends AppCompatActivity {

    Deck deck;
    Gson gson = new Gson();
    String fileName = "localDeckLibrary.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);
        EditText editDeckName = findViewById(R.id.editText);
        Intent sentIntent = this.getIntent();
        // This gets the Deck object sent by the previous activity through a Json string
        Deck compareDeck = gson.fromJson(sentIntent.getStringExtra("DECK"), Deck.class);
        if (compareDeck != null)
            deck = compareDeck;
        else if (compareDeck == null)
            deck = new Deck();
        editDeckName.setText(deck.deckName);
    }

    public Library getMasterLibrary() throws IOException
    {

        Log.d("THIS ACTIVITY", "You called getMasterLibrary, this function will cause the" +
                " program to crash if no file named masterLibrary.txt exists at /data/user/0/com.example.flashdashpersonal/files/masterLibrary.txt");
        Library masterLibrary = null;
        InputStream inputStream = getApplicationContext().openFileInput("localDeckLibrary.txt");
        Log.d("THIS ACTIVITY", "file exists or was created");

        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(currentLine + "\n");
            }

            inputStream.close();
            masterLibrary = gson.fromJson(stringBuilder.toString(), Library.class);
        }

        return masterLibrary;
    }

    public void saveDeck(View view) throws IOException
    {
        Library masterLibrary = getMasterLibrary();

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
