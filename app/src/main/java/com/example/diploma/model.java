package com.example.diploma;

public class model {
    String title, date2, author;
    String amount2;
    model() {
    }

    public model(String title,String prj_end_date, String author, String required_amount) {

        this.title = title;
        this.date2 = prj_end_date;
        this.author = author;
        this.amount2 = required_amount;
        //this.purl=purl;
    }


    public String getTitle() { return title; }

    public String getDate2() { return date2; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAmount2() {
        return amount2;
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
