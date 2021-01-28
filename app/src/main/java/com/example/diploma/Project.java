package com.example.diploma;

public class Project {
    public String title, description;
    public int required_amount;
    public String category = "Default";
    public boolean isCharity;
    public String author;

    public Project() {
    }


    public Project(String title, String description, int required_amount, String category, boolean isCharity, String author) {
        this.title = title;
        this.description = description;
        this.required_amount = required_amount;
        this.category = category;
        this.isCharity = isCharity;
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

    public int getRequired_amount() {
        return required_amount;
    }

    public void setRequired_amount(int required_amount) {
        this.required_amount = required_amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCharity() {
        return isCharity;
    }

    public void setCharity(boolean charity) {
        isCharity = charity;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
