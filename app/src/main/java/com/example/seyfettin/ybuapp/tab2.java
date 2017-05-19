package com.example.seyfettin.ybuapp;

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

public class tab2 extends Fragment {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItem = new ArrayList<String>();
    private ArrayList<String> listLink = new ArrayList<String>();
//    private ProgressDialog progressDialog;
    private String URL = "http://ybu.edu.tr/muhendislik/bilgisayar/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        listView = (ListView) rootView.findViewById(R.id.duyurulist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(listLink.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        adapter=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                listItem);

        new Duyuru().execute();

        return rootView;
    }

    private class Duyuru extends AsyncTask<Void, Void, Void> {

        Elements btn;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                listItem.clear();
                Document doc = Jsoup.connect(URL).get();
                Element div = doc.select("div.contentAnnouncements").get(0);
                btn = div.select("div.caButton");
                Elements rows = div.select("div.contentAnnouncements div.cncItem");

                int i = 0;
                while(rows.get(i).text() != null){
                    listItem.add(rows.get(i).text().toString());
                    listLink.add(URL + rows.get(i).select("a").attr("href").toString());
                    i++;
                    Log.d("aa", "tab2 try");
                }


            } catch (Exception e) {
                Log.d("aa","tab2 exception");
                e.printStackTrace();
                listItem.add(btn.text().toString());
                listLink.add(URL + btn.select("a").attr("href").toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listView.setAdapter(adapter);
//            btnTum.setText(btnText);
//            progressDialog.dismiss();
        }
    }
}
