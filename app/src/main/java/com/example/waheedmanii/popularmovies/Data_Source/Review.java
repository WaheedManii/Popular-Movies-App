package com.example.waheedmanii.popularmovies.Data_Source;

/**
 * Created by Waheed Manii on 17-Aug-16.
 */
public class Review {


    private String author ;
    private String content ;


    public Review() {
    }


    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }





    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}



