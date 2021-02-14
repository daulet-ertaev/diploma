package com.example.diploma;

public class ProjectCreateHelpClass {
    public String title, description;
    public int required_amount;
    public String category = "Default";
    public String isCharity;
    public String author;
    public String isChecked;
    public String dateprojectend;

    public ProjectCreateHelpClass() {
    }


    public ProjectCreateHelpClass(String title, String description, int required_amount, String category, boolean charity, String author, String ischecked, String dateprojectend) {
        this.title = title;
        this.description = description;
        this.required_amount = required_amount;
        this.category = category;
        this.isCharity = String.valueOf(charity);
        this.author = author;
        isChecked = ischecked;
        this.dateprojectend = dateprojectend;
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


    public String getIsCharity() {
        return isCharity;
    }

    public void setIsCharity(String charity) {
        this.isCharity = charity;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String ischecked) {
        this.isChecked = ischecked;
    }


    public String getDateprojectend() {
        return dateprojectend;
    }

    public void setDateprojectend(String dateprojectend) {
        this.dateprojectend = dateprojectend;
    }
}
