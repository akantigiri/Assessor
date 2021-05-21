package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class strngadapter extends ArrayAdapter {
    public strngadapter(@NonNull Context context, ArrayList<strng> resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_layout, parent, false);
        }

        strng currentAndroidFlavor = (strng) getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.homet1a);
        nameTextView.setText(currentAndroidFlavor.gets());
        TextView nameTextViewy = (TextView) listItemView.findViewById(R.id.homet1b);
        nameTextViewy.setText(currentAndroidFlavor.getv());


        return listItemView;
    }
}
