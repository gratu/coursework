package com.example.newsmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 1 on 20.4.2017.
 */
public class MainActivity extends AppCompatActivity {
    ProgressDialog progress;


    GetRSSDataTask task;
    String feedUrl;
    protected MainActivity context;
    SharedPreferences mFeeds;
    ArrayList<RssItem> rssFeed;
    ArrayAdapter<RssItem> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFeeds = getSharedPreferences("Feeds", Context.MODE_PRIVATE);
        context = this;
        ArrayList<String> feeds;

        if (mFeeds.contains("feed0")) {
            feeds = loadList();
        } else {
            feedUrl = "https://news.yandex.ru/world.rss";
            SharedPreferences.Editor editor = mFeeds.edit();
            editor.putString("feed0", feedUrl);
            editor.apply();
            feeds = new ArrayList<String>();
            feeds.add(feedUrl);
        }
        rssFeed = new ArrayList<RssItem>();
        ListView itcItems = (ListView) findViewById(R.id.listMainView);
        adapter = new MyAdapter(context, rssFeed);
        itcItems.setAdapter(adapter);
        itcItems.setOnItemClickListener(new ListListener(rssFeed, context));

        progress = new ProgressDialog(this);
       for(String i:feeds) {
            task = new GetRSSDataTask();
            task.execute(i);
       }

    }

    private ArrayList<String> loadList() {
        ArrayList<String> feeds = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            String feed = "feed" + i;
            if (mFeeds.contains(feed)) {
                feeds.add(mFeeds.getString(feed, ""));
            }
        }

        return feeds;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }






    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
        @Override
        protected List<RssItem> doInBackground(String... urls) {
            publishProgress(null);
            try {

                RssReader rssReader = new RssReader(urls[0]);

                return rssReader.getItems();
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            progress.show();
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {
            progress.dismiss();
            if (result == null) {
            }

            rssFeed.addAll(result);
            adapter.notifyDataSetChanged();

        }
    }


}