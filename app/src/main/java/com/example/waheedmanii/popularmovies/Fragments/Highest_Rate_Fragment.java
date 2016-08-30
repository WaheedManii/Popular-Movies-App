package com.example.waheedmanii.popularmovies.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.waheedmanii.popularmovies.Adapters.GridAdapter;
import com.example.waheedmanii.popularmovies.Data_Source.Movie;
import com.example.waheedmanii.popularmovies.Movie_Details;
import com.example.waheedmanii.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Highest_Rate_Fragment extends Fragment {

    GridView gridView ;
    List<Movie> needed_data ;
    ProgressDialog Wait ;



    public Highest_Rate_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_highest__rate_, container, false);

        gridView = (GridView) V.findViewById(R.id.gridView_highestRate);
        needed_data = new ArrayList<>();

        if( isOnline()){
            new Fetch_Movies_Data().execute();
        }else {
            Toast.makeText(getActivity(), "Please Check Your Connection,Then Press On Refresh Icon", Toast.LENGTH_SHORT).show();
        }


      gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
              Movie M2 = (Movie) parent.getItemAtPosition(i);
              Intent M2_I = new Intent(getActivity() , Movie_Details.class);
              M2_I.putExtra("Copy_From_Movie" , M2 ) ;
              startActivity(M2_I);
          }
      });

     return V ;
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public class Fetch_Movies_Data extends AsyncTask < Void , Void , List<Movie> > {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();


          Wait = ProgressDialog.show(getActivity() , "Loading" , "Please Wait" , true , false) ;
          Log.i("PROGRESS DIALOG " , " PROCCESSING ") ;
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {



            StringBuilder sb = new StringBuilder();
            String Url = "http://api.themoviedb.org/3/movie/top_rated?" ;
            String API_KEY_BARAM = "api_key" ;
            String MY_KEY = "ef3e6fdee84de8f265137d6a9141329a" ;

            Uri URL_AFTER_PARSE = Uri.parse(Url).buildUpon().appendQueryParameter(API_KEY_BARAM , MY_KEY).build();

            URL url = null ;
            try {
               url = new URL(URL_AFTER_PARSE.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection con = null ;
            try {
                con = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            con.setDoInput(true);



            try {
                con.connect();
            } catch (IOException e) {
                e.printStackTrace();


            }

            try {
                con.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }


            BufferedReader BR = null ;
            try {
                InputStream IS = con.getInputStream();
                InputStreamReader ISR = new InputStreamReader(IS);
                 BR = new BufferedReader(ISR);
                if (IS == null){
                    Log.i("CHECK INPUT STREAM" , "INPUT STREAM = NULL");
                    return null ;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            String Line = "" ;
            try {
                while ((Line = BR.readLine()) != null){
                      sb.append(Line);

                    Log.i("CHECK STRING BUFFER" , "I FOUND OUTPUT");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            String Result = sb.toString() ;

            try {
                JSONObject jsonObject = new JSONObject(Result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0 ; i <jsonArray.length() ; i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String poster_path = jsonObject1.getString("poster_path");
                    String overview = jsonObject1.getString("overview");
                    String release_date = jsonObject1.getString("release_date");
                    int  id = jsonObject1.getInt("id");
                    String title = jsonObject1.getString("original_title");
                    String vote_average = jsonObject1.getString("vote_average");

                     needed_data.add(new Movie(poster_path, overview, release_date, id, title, vote_average));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (con != null) {
                con.disconnect();
            }     if (BR != null) {
                try {
                    BR.close();
                } catch (final IOException e) {
                    Log.e("error", "Error closing stream", e);
                }
            }



            return needed_data ;
        }


        @Override
        protected void onPostExecute(List<Movie> needed_data) {
            super.onPostExecute(needed_data);


           gridView.setAdapter(new GridAdapter(needed_data , getContext()));
            Wait.dismiss();

        }



    }


}
