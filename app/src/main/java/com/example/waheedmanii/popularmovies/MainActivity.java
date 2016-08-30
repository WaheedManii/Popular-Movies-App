package com.example.waheedmanii.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.waheedmanii.popularmovies.Data_Source.Movie;
import com.example.waheedmanii.popularmovies.Fragments.Favourite_Movies_Fragment;
import com.example.waheedmanii.popularmovies.Fragments.Highest_Rate_Fragment;
import com.example.waheedmanii.popularmovies.Fragments.Movie_Details_Fragment;
import com.example.waheedmanii.popularmovies.Fragments.Popularity_Movies_Fragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Popularity_Movies_Fragment.Callback {

    List<Movie> needed_data = new ArrayList<>();
    ViewPager viewPager;
    TabLayout tabs;
    boolean mTwoPane;
    FragmentManager fm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
         fm = getSupportFragmentManager();
        viewPager.setAdapter(new MyPagerAdapter(fm));

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

       if (!isOnline()){
           viewPager.setCurrentItem(2) ;
       }

        if(findViewById(R.id.Movie_Detail_Fragment)!=null){
            mTwoPane = true;
        }else{
            mTwoPane = false;
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.ref :
                viewPager.setAdapter(new MyPagerAdapter(fm));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectMovie(Movie m , int check) {

        Log.i("VALUE OF CHECK IN M_A" , String.valueOf(check));
        if (!mTwoPane) {
            Intent M_I = new Intent(MainActivity.this, Movie_Details.class);
            M_I.putExtra("Copy_From_Movie", m);
            M_I.putExtra("Check_Saving_Process" , check);
            startActivity(M_I);
        }else {
            Movie_Details_Fragment Fragment = Movie_Details_Fragment.getInstance(m , check);
            getSupportFragmentManager().beginTransaction().replace(R.id.Movie_Detail_Fragment , Fragment).commit();
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment Frag = null;

            switch (position) {

                case 0:
                    Frag = new Popularity_Movies_Fragment();
                    break;
                case 1:
                    Frag = new Highest_Rate_Fragment();
                    break;
                case 2:
                    Frag = new Favourite_Movies_Fragment();
                    break;
            }

            return Frag;
        }


        @Override
        public int getCount() {

            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String Title = "";
            switch (position) {
                case 0:
                    Title = " Popularity Movie ";
                    break;
                case 1:
                    Title = "Highest Rate ";
                    break;
                case 2:
                    Title = " Favourites ";
                    break;

            }
            return Title;
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}


