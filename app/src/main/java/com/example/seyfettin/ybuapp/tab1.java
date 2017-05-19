package com.example.seyfettin.ybuapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class tab1 extends Fragment {
    private ListView listView;
    private ArrayAdapter<String> yemekAdapter;
    private ArrayList<String> yemekListItem = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private String URL = "http://ybu.edu.tr/sks/";

//    public boolean isOnline() {
//        ConnectivityManager cm =
//                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        listView = (ListView) rootView.findViewById(R.id.yemeklist);

        yemekAdapter=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                yemekListItem);

        new Yemek().execute();
//        if(isOnline())
//            new Yemek().execute();
//        else{
//            Toast.makeText(getContext(), "İnternet bağlantısı sağlanamadı!", Toast.LENGTH_SHORT).show();
//        }

        return rootView;
    }

    private class Yemek extends AsyncTask<Void, Void, Void> {

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
                yemekListItem.clear();
                Document doc = Jsoup.connect(URL).get();
                Element div = doc.select("table").get(0);
                Elements rows = div.select("td");
                int i = 2;
                while(rows.get(i).text() != null){
                    yemekListItem.add(rows.get(i).text().toString());
                    i++;
                    Log.d("aa", "tab1 try");
                }

            } catch (Exception e) {
                Log.d("aa","tab1 exception");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listView.setAdapter(yemekAdapter);
            progressDialog.dismiss();
        }


    }


}