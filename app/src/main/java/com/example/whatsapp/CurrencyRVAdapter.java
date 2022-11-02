package com.example.whatsapp;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Handler;


public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder> {
    private ArrayList<CurrencyRVModal> currencyRVModalArrayList;
    private Context context;
    private static DecimalFormat df1 = new DecimalFormat("#.####");
    private static DecimalFormat df3 = new DecimalFormat("#.##");


    public CurrencyRVAdapter(ArrayList<CurrencyRVModal> currencyRVModalArrayList, Context context) {
        this.currencyRVModalArrayList = currencyRVModalArrayList;
        this.context = context;

    }

    public void filterList(ArrayList<CurrencyRVModal> filteredList){
        currencyRVModalArrayList = filteredList;
        notifyDataSetChanged();
    }
    private void changeColor(TextView tv,double d){
        if(d<0){
            tv.setTextColor(Color.parseColor("#FF0000"));
        }else{
            tv.setTextColor(Color.parseColor("#008000"));
        }
    }

    @NonNull
    @Override
    public CurrencyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.currency_nv_item,parent,false);

        return new CurrencyRVAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CurrencyRVAdapter.ViewHolder holder, int position) {
        CurrencyRVModal currencyRVModal= currencyRVModalArrayList.get(position);
        holder.currencyNameTV.setText(currencyRVModal.getName());
        holder.symblolTV.setText(currencyRVModal.getSymbol());
        holder.rateTV.setText("$"+df1.format(currencyRVModal.getPrice()));
        holder.setImageView(currencyRVModal.getLogo());

        holder.sevendays.setText(df3.format(currencyRVModal.getSevendays())+"%");
        changeColor(holder.sevendays,currencyRVModal.getSevendays());
        holder.oneday.setText(df3.format(currencyRVModal.getOneday())+"%");
        changeColor(holder.oneday,currencyRVModal.getOneday());
        holder.hour.setText(df3.format(currencyRVModal.getHour())+"%");
        changeColor(holder.hour,currencyRVModal.getHour());
        holder.idRLCurrency.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,DetailsCoinsActivity.class);
                intent.putExtra("logo",currencyRVModal.getLogo());
                intent.putExtra("symbol",currencyRVModal.getSymbol());
                intent.putExtra("rateTV",currencyRVModal.getPrice());
                intent.putExtra("name",currencyRVModal.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return currencyRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout idRLCurrency;
        private TextView currencyNameTV,symblolTV,rateTV,sevendays,oneday,hour;
        ImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyNameTV=itemView.findViewById(R.id.idTvCurrencyName);
            symblolTV=itemView.findViewById(R.id.idTVSymbol);
            rateTV=itemView.findViewById(R.id.idTvCurrencyRate);


            sevendays=itemView.findViewById(R.id.sevendays);
            oneday=itemView.findViewById(R.id.oneday);
            hour=itemView.findViewById(R.id.hour);
            idRLCurrency=itemView.findViewById(R.id.idRLCurrency);
        }
        public void setImageView(String url){
            logo=itemView.findViewById(R.id.logo);
            Glide.with(context).load(url).into(logo);


        }


    }


}
