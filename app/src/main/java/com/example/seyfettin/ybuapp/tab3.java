package com.example.seyfettin.ybuapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by seyfettin on 16.05.2017.
 */

public class tab3 extends Fragment{

    private ListView HaberListView;
    private ArrayAdapter<String> HaberAdapter;
    private ArrayList<String> HaberListItem = new ArrayList<String>();
    private ArrayList<String> HaberListLink = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private String URL = "http://ybu.edu.tr/muhendislik/bilgisayar/";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tab3, container, false);
            HaberListView = (ListView) rootView.findViewById(R.id.haberList);
            HaberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Uri uri = Uri.parse(HaberListLink.get(position));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            HaberAdapter=new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1,
                    HaberListItem);

            new Haber().execute();

            return rootView;
        }

    private class Haber extends AsyncTask<Void, Void, Void> {
        Elements btn;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("YbuApp");
            progressDialog.setMessage("Veri Çekiliyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HaberListItem.clear();
                Document doc = Jsoup.connect(URL).get();
                Element div = doc.select("div.cnContent").get(0);
                btn = div.select("div.cnButton");

                Elements rows = div.select("div.cnContent div.cncItem");
                int i = 0;
                while(rows.get(i).text() != null){
                    HaberListItem.add(rows.get(i).text().toString());
                    HaberListLink.add(URL + rows.get(i).select("a").attr("href").toString());
                    i++;
                    Log.d("aa", "tab3 try");
                }


            } catch (Exception e) {
                Log.d("aa","tab3 exception");
                e.printStackTrace();
                HaberListItem.add(btn.text().toString());
                HaberListLink.add(URL + btn.select("a").attr("href").toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            HaberListView.setAdapter(HaberAdapter);
            progressDialog.dismiss();
        }

    }

}
