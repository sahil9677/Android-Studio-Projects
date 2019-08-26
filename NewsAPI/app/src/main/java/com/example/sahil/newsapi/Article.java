package com.example.sahil.newsapi;

public class Article {
    String title, urlToImage, publishedAt, description;

    public Article() {

    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
