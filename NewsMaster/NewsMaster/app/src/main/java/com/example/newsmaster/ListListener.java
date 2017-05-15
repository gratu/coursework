package com.example.newsmaster;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;



public class ListListener implements OnItemClickListener {

    List<RssItem> listItems;
    Activity activity;

    public ListListener(List<RssItem> aListItems, Activity anActivity) {
        listItems = aListItems;
        activity  = anActivity;
    }
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(listItems.get(pos).getLink()));

        activity.startActivity(i);

    }

}