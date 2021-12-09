package com.example.budgetsoftware;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


public class PurchaseModel {
    private int id;
    private String title;
    private int price;
    private String strCurrentTime;


    public PurchaseModel(int id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        String strDate = formatter.format(date);

        this.strCurrentTime = strDate;
    }

    public int getId() {
        return id;
    }

    public String getCurrentTime() {


        return strCurrentTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    //To string


    @Override
    public String toString() {
        return "PurchaseModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + strCurrentTime + '\'' +
                ", price=" + price +
                '}';
    }


}
