package com.example.waheedmanii.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.waheedmanii.popularmovies.Data_Source.Movie;
import com.example.waheedmanii.popularmovies.Fragments.Movie_Details_Fragment;

public class Movie_Details extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent M_I = getIntent();
        Movie m = M_I.getExtras().getParcelable("Copy_From_Movie");
        int check = M_I.getIntExtra("Check_Saving_Process" , 1);
        Log.i("VALUE OF CHECK IN D_A" , String.valueOf(check));
        Movie_Details_Fragment Fragment = Movie_Details_Fragment.getInstance(m , check);
        getSupportFragmentManager().beginTransaction().replace(R.id.Movie_Detail_Fragment , Fragment).commit();

    }



    }








