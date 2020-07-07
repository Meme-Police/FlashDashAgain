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
    public List<Deck> deckList = new ArrayList<Deck>();

    public void loadMasterLibrary(Context c) throws IOException
    {
        /****************************************************************************************
         * NGL, I felt pretty smart for realising I could save all local deck object to one file
         ****************************************************************************************/
        Gson gson = new Gson();
        Log.d("ALL", "You called getMasterLibrary, this function will cause the" +
                " program to crash if no file named masterLibrary.txt exists at /data/user/0/com.example.flashdashpersonal/files/masterLibrary.txt");
        Library masterLibrary = null;
        InputStream inputStream = c.openFileInput("localDeckLibrary.txt");
        Log.d("ALL", "file exists or was created");

        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine;
            String masterLibraryString;
            Log.d("ALL", "Built bufferedreader and stringbuilder");

            while ((currentLine = bufferedReader.readLine()) != null)
            {
                Log.d("ALL", "Stringbuilder ran one time");
                stringBuilder.append(currentLine + "\n");
            }
            masterLibraryString = stringBuilder.toString();
            if (bufferedReader.readLine() == null)
            {
                Log.d("ALL", "It's all null? Always has been");
                masterLibraryString = gson.toJson(new Library());
            }
            inputStream.close();
            Log.d("ALL", stringBuilder.toString());
            Log.d("ALL", "Is the fileString behind me?");
            masterLibrary = gson.fromJson(stringBuilder.toString(), Library.class);
        }
        else if (inputStream == null)
        {
            Log.d("ALL", "guess inputstream is null");
        }

        deckList = masterLibrary.deckList;
        Log.d("ALL", "decklist set from masterlibrary");
    }

}


