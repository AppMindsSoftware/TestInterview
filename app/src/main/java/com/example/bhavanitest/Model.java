package com.example.bhavanitest;

public class Model {

    String movie_title;
    String movie_thumbnail_image;

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_thumbnail_image() {
        return movie_thumbnail_image;
    }

    public void setMovie_thumbnail_image(String movie_thumbnail_image) {
        this.movie_thumbnail_image = movie_thumbnail_image;
    }

    @Override
    public String toString() {
        return "Model{" +
                "movie_title='" + movie_title + '\'' +
                ", movie_thumbnail_image='" + movie_thumbnail_image + '\'' +
                '}';
    }
}
