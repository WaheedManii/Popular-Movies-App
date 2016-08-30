package com.example.waheedmanii.popularmovies.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.waheedmanii.popularmovies.Data_Source.Movie;
import com.example.waheedmanii.popularmovies.Data_Source.MovieTable;
import com.example.waheedmanii.popularmovies.Data_Source.Tralier;
import com.example.waheedmanii.popularmovies.R;
import com.example.waheedmanii.popularmovies.Traliers_And_Reviews.Fetch_Reviews;
import com.example.waheedmanii.popularmovies.Traliers_And_Reviews.Fetch_Trailers;
import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;


public class Movie_Details_Fragment extends Fragment {


    String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    TextView TITLE ;
    TextView AVG_RATE ;
    TextView RELEASE_DATE ;
    TextView OVERVIEW ;
    ImageView SMALL_POSTER ;
    LinearListView Trailer_list ;
    LinearListView Review_List ;
    ToggleButton Favourite ;
    private Movie m;
    int check ;



    public Movie_Details_Fragment() {

    }





    public static Movie_Details_Fragment getInstance(Movie m  , int check ){
        Movie_Details_Fragment Fragment = new Movie_Details_Fragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie",m);
        bundle.putInt("recive" , check);
        Log.i("VALUE OF CHECK IN D_F" , String.valueOf(check));
        Fragment.setArguments(bundle);

        return Fragment ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_movie_details, container, false);





        TITLE = (TextView) v.findViewById(R.id.Movie_Title);
        AVG_RATE = (TextView) v.findViewById(R.id.Movie_Rating);
        RELEASE_DATE = (TextView) v.findViewById(R.id.Movie_Release_Date);
        OVERVIEW = (TextView) v.findViewById(R.id.Movie_Overview);
        SMALL_POSTER = (ImageView) v.findViewById(R.id.Movie_Small_Poster);
        Trailer_list = (LinearListView) v.findViewById(R.id.Trailers_List);
        Review_List = (LinearListView) v.findViewById(R.id.Reviews_List);
        Favourite = (ToggleButton) v.findViewById(R.id.Favourite_Button);




        Trailer_list.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView parent, View view, int position , long id) {
                if (parent.getId() == R.id.Trailers_List){

                    Tralier item = (Tralier) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(item.getKey()));
                     intent = Intent.createChooser(intent, " Play With ");
                    startActivity(intent);

                }
            }
        });

        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




         m = getArguments().getParcelable("movie");
        check = getArguments().getInt("recive");

        Log.i("CHECK RECIVE VALUE" , String.valueOf(check));

        if (check == 0){

        }else {

            new Fetch_Reviews(getActivity(), m, Review_List);
            new Fetch_Trailers(getActivity(), m, Trailer_list);
        }


        TITLE.setText(m.getTitle());
        AVG_RATE.setText("Rating [ " + m.getVote_average() + " / 10 ]");
        RELEASE_DATE.setText("Date [ " + m.getRelease_date() + " ]");
        OVERVIEW.setText(m.getOverview());

        Picasso.with(getActivity()).load(IMAGE_URL+m.getPoster_path()).into(SMALL_POSTER);

        String [] title = {"col_title"};
        String selction = "col_title"+"=?";
        String [] args = {m.getTitle()};
        Cursor c = getActivity().getContentResolver().query(MovieTable.CONTENT_URI, title, selction, args , null);



        if (c.moveToFirst()){

            Favourite.setBackgroundColor(Color.parseColor("#00838F"));
            Favourite.setChecked(true);
        }



        Favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Favourite.setBackgroundColor(Color.parseColor("#00838F"));
                    getActivity().getContentResolver().insert(MovieTable.CONTENT_URI , MovieTable.getContentValues(m ,false)) ;

                    Toast.makeText(getActivity(), "Saved In Favourite", Toast.LENGTH_SHORT).show();
                } else if (!isChecked){
                    Favourite.setBackgroundColor(Color.parseColor("#90CAF9"));
                    String selction = "col_title"+"=?";
                    String [] args = {m.getTitle()};
                    getActivity().getContentResolver().delete(MovieTable.CONTENT_URI, selction , args );
                    Toast.makeText(getActivity(), "Removed", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }




}
