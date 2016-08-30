package com.example.waheedmanii.popularmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.waheedmanii.popularmovies.Data_Source.Review;
import com.example.waheedmanii.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by Waheed Manii on 21-Aug-16.
 */
public class ReviewAdapter extends BaseAdapter {

    Context context ;
    ArrayList<Review> review_attributes ;

    public ReviewAdapter(Context context, ArrayList<Review> review_attributes) {

        this.context = context ;
        this.review_attributes = review_attributes ;

    }

    @Override
    public int getCount() {
        return review_attributes.size() ;
    }

    @Override
    public Review getItem(int i) {
        return review_attributes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

             viewHolder holder = null ;
        if (view == null){
            view  = LayoutInflater.from(context).inflate(R.layout.review_row, viewGroup, false);
            holder = new viewHolder(view);
            view.setTag(holder);
        }else {
            holder  = (viewHolder) view.getTag();
        }

        Review item = getItem(i);
        holder.Author_Name.setText("Author Name : "+item.getAuthor());
        holder.Movie_Review.setText(item.getContent());
        return view;
    }


    public class viewHolder{

        TextView Author_Name ;
        TextView Movie_Review ;

        public viewHolder(View v) {

            Author_Name = (TextView) v.findViewById(R.id.Author_Name_TextView);
            Movie_Review = (TextView) v.findViewById(R.id.Review_TextView);
        }
    }
}
