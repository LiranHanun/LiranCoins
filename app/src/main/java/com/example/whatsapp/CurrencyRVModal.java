package com.example.whatsapp;

import android.widget.ImageView;

import java.util.ArrayList;

public class CurrencyRVModal {
    private static ArrayList<CurrencyRVModal> userArrayList =new ArrayList<CurrencyRVModal>();
    private String name;
    private String symbol;
    private double price;
    private String logo;
    private double sevendays;
    private double oneday;
    private double hour;
    private int id;
    private double[] prices;
    private int count;




    public CurrencyRVModal(String name, String symbol, double price,String logo,double sevendays,double oneday,double hour,int id) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.logo=logo;
        this.sevendays=sevendays;
        this.oneday=oneday;
        this.hour=hour;
        this.id=id;
        userArrayList.add(this);
        prices=new double[5];
        this.count=0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setPrice(double price) {
            this.price = price;
        }




    public double getPrice() {
        return price;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public double getHour() {
        return hour;
    }

    public double getOneday() {
        return oneday;
    }

    public double getSevendays() {
        return sevendays;
    }



    public void setHour(double hour) {
        this.hour = hour;
    }

    public String getLogo() {
        return logo;
    }

    public void setOneday(double oneday) {
        this.oneday = oneday;
    }

    public void setSevendays(double sevendays) {
        this.sevendays = sevendays;
    }

    public ArrayList<CurrencyRVModal> getUserArrayList() {
        return userArrayList;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setUserArrayList(ArrayList<CurrencyRVModal> userArrayList) {
        this.userArrayList = userArrayList;
    }
    public static ArrayList<CurrencyRVModal> getArray(){
        ArrayList<CurrencyRVModal> inventory = new ArrayList<CurrencyRVModal>();
        for(CurrencyRVModal i : userArrayList)
        {
                inventory.add(i);
        }
        return inventory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double[] getPrices() {
        return prices;
    }

    public void setPrices(double[] prices) {
        this.prices = prices;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
