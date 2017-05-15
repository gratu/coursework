package com.example.newsmaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 1 on 20.4.2017.
 */
public class MyAdapter extends ArrayAdapter<RssItem> {
    public MyAdapter(Context context, List<RssItem> res) {
        super(context, R.layout.item_main, res);
    }

    static class ViewHolderItem {
        TextView title;
        TextView text;
        TextView pubDate;
        ImageView image;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RssItem item = getItem(position);
        ViewHolderItem viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_main, null);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.pubDate = (TextView) convertView.findViewById(R.id.pubDate);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        viewHolder.title.setText(item.getTitle());
        viewHolder.text.setText(item.getText());
        viewHolder.pubDate.setText(item.getPubDate());
        if (item.getImglink()!= null){
            ImgInfo imgInfo = new ImgInfo(item.getImglink(), viewHolder);
            GetImg task = new GetImg();
            task.execute(imgInfo);
        }
        return convertView;
    }
    private class ImgInfo{
        String url;
        ViewHolderItem holder;
        public ImgInfo(String url, ViewHolderItem holder){
            this.url = url;
            this.holder = holder;
        }
        public String getUrl(){
            return url;
        }
        public ViewHolderItem getHolder(){
            return holder;
        }
    }
    private class GetImg extends AsyncTask<ImgInfo, Void, Bitmap > {
        ViewHolderItem savelink;
        @Override
        protected Bitmap doInBackground(ImgInfo... info) {
            // TODO Auto-generated method stub
            try{
                URL feedImage= new URL(info[0].getUrl());
                savelink = info[0].getHolder();
                HttpURLConnection conn= (HttpURLConnection)feedImage.openConnection();
                InputStream is  = conn.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(is);
                return img;

            }catch (Exception e) {
                // TODO: handle exception

            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            savelink.image.setImageBitmap(result);
        }


    }
}