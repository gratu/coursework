package com.example.newsmaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity {
    View feedLayout;
    Button okButton;
    EditText feedEdit;
    Button addFeed;
    ListView feedsListView;
    ArrayList<String> feedList;
    ArrayAdapter<String> adapter;
    SharedPreferences mFeeds;
    int mSlot;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mFeeds = getSharedPreferences("Feeds", Context.MODE_PRIVATE);
        feedsListView = (ListView) findViewById(R.id.feedsListView);
        feedLayout = findViewById(R.id.editFeedLayout);
        addFeed = (Button) findViewById(R.id.addFeedButton);
        feedLayout.setVisibility(View.GONE);
        okButton = (Button) findViewById(R.id.addButton);
        feedEdit = (EditText) findViewById(R.id.editFeedEditText);
        feedList = new ArrayList<String>();

        loadList();
    }
private void loadList(){
    for(int i=0; i<10; i++) {
        String feed = "feed"+i;
        if (mFeeds.contains(feed)) {
            feedList.add(mFeeds.getString(feed, ""));
        }
    }
    adapter = new FeedListAdapter(this, feedList);

    feedsListView.setAdapter(adapter );
    mSlot = feedList.size();
}
    public void onAddClick(View v){
        try {
       String feedUrl = feedEdit.getText().toString();
            URL url = new URL(feedUrl);


            //& getFileExtension(url.getFile()).equals("xml")
        if (Patterns.WEB_URL.matcher(feedUrl).matches() & getFileExtension(url.getFile())) {
            feedLayout.setVisibility(View.GONE);
            addFeed.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = mFeeds.edit();
            String feed = "feed" + mSlot++;
            editor.putString(feed, feedUrl);
            editor.apply();
            feedList.add(feedUrl);
            adapter.notifyDataSetChanged();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }else{
            Toast.makeText(this, "Please enter a valid Url adress ", Toast.LENGTH_SHORT).show();
        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private boolean getFileExtension(String fileName) {

        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).equals("rss")
                    || fileName.substring(fileName.lastIndexOf(".") + 1).equals("xml");

        }
        else return false;

    }
    public void onDelleteClick(View v){
       feedList.remove((int)v.getTag());
       adapter.notifyDataSetChanged();
        mSlot--;
        SharedPreferences.Editor editor = mFeeds.edit();
        for( int i=0;i<10; i++) {
            String feed = "feed" + i;
            if(i<mSlot){
                editor.putString(feed, feedList.get(i));

            }else{
                editor.remove(feed);
            }
        }
        editor.apply();
        mSlot = feedList.size();
    }
    public void onAddFeedClick(View v){
        feedLayout.setVisibility(View.VISIBLE);
        addFeed.setVisibility(View.GONE);
    }
}
