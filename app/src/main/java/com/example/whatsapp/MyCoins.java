package com.example.whatsapp;

import com.google.firebase.database.IgnoreExtraProperties;

public class MyCoins {
    private String nameCoin;
    private String howMuchCoins;
    private String value;


    public MyCoins(String nameCoin,String howMuchCoins,String value){
        this.howMuchCoins=howMuchCoins;
        this.nameCoin=nameCoin;
        this.value=value;
    }

    public String getHowMuchCoins() {
        return howMuchCoins;
    }

    public void setHowMuchCoins(String howMuchCoins) {
        this.howMuchCoins = howMuchCoins;
    }

    public String getNameCoin() {
        return nameCoin;
    }

    public void setNameCoin(String nameCoin) {
        this.nameCoin = nameCoin;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
