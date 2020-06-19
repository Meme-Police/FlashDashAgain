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

    Deck deck = new Deck();
    public void literallyMagic(View view)
    {
        // initialise new intent
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, 41);
        //placeholder
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri deckURI;
        Gson gson = new Gson();
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
