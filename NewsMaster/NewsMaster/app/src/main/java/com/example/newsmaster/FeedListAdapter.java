package com.example.newsmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.data;


public class FeedListAdapter extends ArrayAdapter<String> {
    public FeedListAdapter(Context context, List<String> res) {
        super(context, R.layout.item_settings, res);
    }
    static class ViewHolderItem {
        TextView feedTextView;
        Button delleteButton;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
       String item = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_settings, null);
            viewHolder = new ViewHolderItem();
            viewHolder.feedTextView = (TextView) convertView.findViewById(R.id.feedTextView);
            viewHolder.delleteButton = (Button) convertView.findViewById(R.id.delleteButton);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        viewHolder.feedTextView.setText(item);
        viewHolder.delleteButton.setTag(position);

        return convertView;
    }
}
