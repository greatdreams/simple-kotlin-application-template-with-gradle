package com.greatdreams.kotlin.template.model;

public class ApplicationInformation {
    private String id;
    private String name;
    private String author;
    private String email;

    public ApplicationInformation(String id, String name, String author, String email) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "application information: id-" + id + ", name-" +
                name + ", author-" + author + ", email-" + email;
    }
}
