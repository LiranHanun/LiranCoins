package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyCoinsAdapter extends  RecyclerView.Adapter<MyCoinsAdapter.ViewHolder>{
    private static DecimalFormat df1 = new DecimalFormat("########");
    ArrayList<MyCoins> myCoinsList;
    Context context;

    public MyCoinsAdapter(ArrayList<MyCoins> myCoinsList,Context context){
        this.myCoinsList=myCoinsList;
        this.context=context;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.activity_my_coins_adapter,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCoins myCoins=myCoinsList.get(position);
        if(myCoins.getHowMuchCoins().length()>9) {
            holder.howMuchCoinsTV.setText(myCoins.getHowMuchCoins().substring(0, 9));
        }else{
            holder.howMuchCoinsTV.setText(myCoins.getHowMuchCoins());
        }
        holder.coinNameTV.setText(myCoins.getNameCoin());
        holder.moneySpend.setText(myCoins.getValue());
    }

    @Override
    public int getItemCount() {
        return myCoinsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView coinNameTV;
        TextView howMuchCoinsTV;
        TextView moneySpend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coinNameTV=itemView.findViewById(R.id.nameCoinTV);
            howMuchCoinsTV=itemView.findViewById(R.id.HowMuchCoinsTV);
            moneySpend=itemView.findViewById(R.id.moneySpend);

        }
    }


}