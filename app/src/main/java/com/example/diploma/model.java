package com.example.diploma;

public class model {
    String title, dateprojectend, author;
    String required_amount;
    model() {
    }

    public model(String title,String prj_end_date, String author, String required_amount) {

        this.title = title;
        this.dateprojectend = prj_end_date;
        this.author = author;
        this.required_amount = required_amount;
        //this.purl=purl;
    }


    public String getTitle() { return title; }

    public String getDateprojectend() { return dateprojectend; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRequired_amount() {
        return required_amount;
    }

    //public void setAmount(String purl) {
   //     this.required_amount111= required_amount111 ;
   // }

 /*   public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }*/
}
