package com.example.flashdashpersonal;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Library
{
    List<Deck> deckList = new ArrayList<Deck>();



    public void loadMasterLibrary(Context c) throws IOException
    {
        /****************************************************************************************
         * NGL, I felt pretty smart for realising I could save all local deck object to one file
         ****************************************************************************************/
        Gson gson = new Gson();
        Log.d("THIS ACTIVITY", "You called getMasterLibrary, this function will cause the" +
                " program to crash if no file named masterLibrary.txt exists at /data/user/0/com.example.flashdashpersonal/files/masterLibrary.txt");
        Library masterLibrary = null;
        InputStream inputStream = c.openFileInput("localDeckLibrary.txt");
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

        deckList = masterLibrary.deckList;
    }

}


