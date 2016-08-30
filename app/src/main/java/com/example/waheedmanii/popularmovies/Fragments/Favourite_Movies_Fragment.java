package com.example.waheedmanii.popularmovies.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.waheedmanii.popularmovies.Adapters.GridAdapter;
import com.example.waheedmanii.popularmovies.Data_Source.Movie;
import com.example.waheedmanii.popularmovies.Data_Source.MovieTable;
import com.example.waheedmanii.popularmovies.MainActivity;
import com.example.waheedmanii.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourite_Movies_Fragment extends Fragment {

    GridView Favourite_GridView ;
    List<Movie> needed_data ;



    public Favourite_Movies_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_favourite_movies, container, false);

          needed_data = new ArrayList<>();
        Favourite_GridView = (GridView) v.findViewById(R.id.gridView_favourite);



        return v ;

    }

    private void View_Favourite_Movies() {




        Cursor c = getActivity().getContentResolver().query(MovieTable.CONTENT_URI, null, null, null, null);
        List<Movie> movieRows = MovieTable.getRows(c ,false);
        c.close();


        Favourite_GridView.setAdapter(new GridAdapter(movieRows , getContext()));
        Favourite_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie M = null;
                M = (Movie) adapterView.getItemAtPosition(i);
                ((MainActivity)getActivity()).selectMovie(M , 0);

            }
        });
    }





    @Override
    public void onResume() {
        super.onResume();
        View_Favourite_Movies();

    }
}
