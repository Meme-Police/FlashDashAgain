package com.example.flashdashpersonal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    public void startActivityWithDeck(final int index)
    {
        Intent originalActivity = this.getIntent();
        Intent intent = new Intent();
        String intendedActivity = originalActivity.getStringExtra("nextActivity");
        if (intendedActivity.equals("EditDeckActivity")) {
            intent = new Intent(this, EditDeckActivity.class);
            intent.putExtra("DECK", gson.toJson(localLibrary.deckList.get(index)));
            startActivity(intent);
            finish();
        }
        else if (intendedActivity.equals("PlayDeckActivity")){
            intent = new Intent(this, ModeSelectActivity.class);
            intent.putExtra("DECK", gson.toJson(localLibrary.deckList.get(index)));
            startActivity(intent);
            finish();
        }
        else if (intendedActivity.equals("DeleteDeck"))
        {
            /*********************************************************
             * Yeah, this is a bad place for a function like this.
             *  But these are extra features I thought up on the fly.
             *********************************************************/
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Delete Deck?");
            alert.setMessage("This will permanently delete the deck");
            alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    localLibrary.deckList.remove(index);
                    try {
                        localLibrary.saveLibrary(getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    finish();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });



            AlertDialog deleteAlert = alert.create();
            deleteAlert.show();

        }
        else if(intendedActivity.equals("Export"))
        {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, gson.toJson(localLibrary.deckList.get(index)));
                share.setType("text/plain");
                startActivity(share);
        }

    }

}
