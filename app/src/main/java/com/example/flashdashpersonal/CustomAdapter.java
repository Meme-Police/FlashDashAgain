package com.example.flashdashpersonal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] deckNames;

    public CustomAdapter(Activity c, String[] deckNames)
    {
        super(c, R.layout.deck_list_item, deckNames);
        context = c;
        this.deckNames = deckNames;
    }

    public View getView(int iterator, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.deck_list_item, null, true);
        TextView deckName = (TextView) rowView.findViewById(R.id.deckNameID);
        deckName.setText(deckNames[iterator]);

        return rowView;
    }


}
