package com.android.mnews.mediastack;

public class Data {
    private String author;
    private String title;
    private String description;
    private String url;
    private String image;
    private String category;
    private String language;
    private String country;
    private String published_at;

    public Data(String author, String title, String description, String url, String image, String category, String language, String country, String published_at) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.image = image;
        this.category = category;
        this.language = language;
        this.country = country;
        this.published_at = published_at;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    @Override
    public String toString() {
        return "Data{" + "author=" + author + ", title=" + title + ", description=" + description + ", url=" + url + ", image=" + image + ", category=" + category + ", language=" + language + ", country=" + country + ", published_at=" + published_at + '}';
    }

}