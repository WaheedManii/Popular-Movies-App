package com.example.waheedmanii.popularmovies.Data_Source;

/**
 * Created by Waheed Manii on 21-Aug-16.
 */
public class Tralier{


     private String name ;
     private String key ;


    public Tralier() {
    }

    public Tralier(String name, String key) {
        this.name = name;
        this.key = key;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
