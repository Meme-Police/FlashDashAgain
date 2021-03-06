package com.example.flashdashpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String fileName = "localDeckLibrary.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkForMasterFile();

    }

    private void checkForMasterFile()
    {
        File file = new File(getApplicationContext().getFilesDir(), fileName );
        boolean fileCreated = false;
        Log.d("ALL", file.toString());
        try {
            fileCreated = file.createNewFile();
        } catch (IOException e) {
            Log.d("ALL", "Error while creating file " + e);
            e.printStackTrace();
        }

        if (fileCreated) {
            Log.d("ALL", "File Created at " + file.getPath());
        }
        
        else {
            Log.d("ALL", "File already exists");
        }

    }

    public void NewDeckActivity(View view)
    {
        Intent intent = new Intent(this, newDeckActivity.class);
        startActivity(intent);
    }

    public void playDeckActivity(View view)
    {
        Intent intent = new Intent(this, DeckSelectActivity.class);
        intent.putExtra("nextActivity", "PlayDeckActivity");
        startActivity(intent);
    }

    public void StartLibraryActivity(View view)
    {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }
}
