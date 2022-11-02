package com.example.whatsapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.whatsapp.databinding.ActivityHomepage3Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


public class Homepage extends DrawerBaseActivity {

    private static final String CHANNEL_ID = "channel_id01";
    public static final int NOTIFICATION_ID = 1;

    Settings s = MainActivity.s;
    private EditText searchEdit;
    private RecyclerView currenciesRV;
    private ProgressBar loadingPB;
    private ArrayList<CurrencyRVModal> currencyRVModalArrayList;
    private CurrencyRVAdapter currencyRVAdapter;

    private Context context;
    ActivityHomepage3Binding ActivityHomepage3Binding;
    List<String> alreadygotmsg = new ArrayList<String>();
    ArrayList<CurrencyRVModal> array = CurrencyRVModal.getArray();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase db;
    DatabaseReference creatorRef;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 60000;//1*60000; // 60000 = 60 sec  ||   1000 = 1s
    String Spercent = s.getPercent().substring(0, s.getPercent().length() - 1);
    double percent = Double.parseDouble(Spercent) / 100; // 0.05=5.00%  ||  0.005 = 0.50%  ||  0.001 = 0.10%  0.0005 = 0.05%
    int aaa = 0;
    private int Integer_Number = -2;
    String limit = "limit=25";
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomepage3Binding = ActivityHomepage3Binding.inflate(getLayoutInflater());
        setContentView(ActivityHomepage3Binding.getRoot());
        allocateActivityTittle("Homepage");
        showNotificationOnDown("LiteCoin");


        showNotificationOnDown("BitCoin");

        context = this;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(Homepage.NOTIFICATION_ID);


        searchEdit = findViewById(R.id.idEdtSearch);
        currenciesRV = findViewById(R.id.idRVcurrencies);
        loadingPB = findViewById(R.id.idPBLoading);
        currencyRVModalArrayList = new ArrayList<>();
        currencyRVAdapter = new CurrencyRVAdapter(currencyRVModalArrayList, this);
        currenciesRV.setLayoutManager(new LinearLayoutManager(this));
        currenciesRV.setAdapter(currencyRVAdapter);

        if (CurrencyRVModal.getArray().isEmpty()) {
            getCurrencyData();
        } else {

            for (CurrencyRVModal item : array) {
                currencyRVModalArrayList.add(item);
                currencyRVAdapter.notifyDataSetChanged();


            }

        }


        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCurremcies(s.toString());
            }
        });
    }


    private void filterCurremcies(String currency) {
        ArrayList<CurrencyRVModal> filteredList = new ArrayList<CurrencyRVModal>();
        for (CurrencyRVModal item : currencyRVModalArrayList) {
            if (item.getName().toLowerCase().contains(currency.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No currency found for search query", Toast.LENGTH_SHORT).show();
        } else {
            currencyRVAdapter.filterList(filteredList);
        }
    }


    private void getCurrencyData() {
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?" + limit;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {

                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        double sevendays = USD.getDouble("percent_change_7d");
                        double oneday = USD.getDouble("percent_change_24h");
                        double hour = USD.getDouble("percent_change_1h");

                        int id = dataObj.getInt("id");

                        String imgUrl = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/info?id=" + id;

                        int b = i;
                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, imgUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response2) {
                                loadingPB.setVisibility(View.GONE);

                                try {

                                    JSONObject imgData = response2.getJSONObject("data");

                                    JSONObject idData = imgData.getJSONObject("" + id + "");

                                    String logo = idData.getString("logo");


                                    currencyRVModalArrayList.add(new CurrencyRVModal(name, symbol, price, logo, sevendays, oneday, hour, b));


                                    currencyRVAdapter.notifyDataSetChanged();


                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Toast.makeText(Homepage.this, "Fail to get the data", Toast.LENGTH_SHORT).show();
                                }


                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String logo = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAA1BMVEX///+nxBvIAAAASElEQVR4nO3BgQAAAADDoPlTX+AIVQEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwDcaiAAFXD1ujAAAAAElFTkSuQmCC";
                                currencyRVModalArrayList.add(new CurrencyRVModal(name, symbol, price, logo, sevendays, oneday, hour, id));
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(Homepage.this, "Fail to get the data...", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<>();
                                headers.put("X-CMC_PRO_API_KEY", "5110d08d-0bbc-42a5-84b2-bbf3500df227");
                                return headers;
                            }

                        };

                        requestQueue.add(jsonObjectRequest2);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Homepage.this, "Fail to get the data", Toast.LENGTH_SHORT).show();
                }
                currencyRVAdapter.notifyDataSetChanged();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loadingPB.setVisibility(View.GONE);
                Toast.makeText(Homepage.this, "Fail to get the data...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "5110d08d-0bbc-42a5-84b2-bbf3500df227");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);


    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                String[] userEmail = user.getEmail().split("@");
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                ArrayList<String> list = new ArrayList<String>();


                DatabaseReference creatorRef = db.child(String.valueOf(userEmail[0])).child("My Coins");
                creatorRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsp : snapshot.getChildren()) {
                            list.add(dsp.getKey());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                ArrayList<CurrencyRVModal> a = CurrencyRVModal.getArray();
                RequestQueue requestQueue = Volley.newRequestQueue(Homepage.this);
                String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?" + limit;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingPB.setVisibility(View.GONE);
                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            for (CurrencyRVModal item : a) {
                                for (int i = 0; i < dataArray.length(); i++) {

                                    JSONObject dataObj = dataArray.getJSONObject(i);

                                    if (dataObj.getString("name").equals(item.getName())) {

                                        JSONObject quote = dataObj.getJSONObject("quote");
                                        JSONObject USD = quote.getJSONObject("USD");
                                        double price = USD.getDouble("price");
                                        double sevendays = USD.getDouble("percent_change_7d");
                                        double oneday = USD.getDouble("percent_change_24h");
                                        double hour = USD.getDouble("percent_change_1h");
                                        item.setPrice(price);
                                        item.setSevendays(sevendays);
                                        item.setOneday(oneday);
                                        item.setHour(hour);

                                        double[] array = item.getPrices();

                                        if(s.getCount()==count) {
                                        if (item.getCount() != 5) {
                                            array[item.getCount()] = price;
                                            item.setPrices(array);
                                            item.setCount(item.getCount() + 1);
                                        } else {
                                            array[0] = array[1];
                                            array[1] = array[2];
                                            array[2] = array[3];
                                            array[3] = array[4];
                                            array[4] = price;
                                            item.setPrices(array);
                                        }
                                            s.setCount(s.toCount());
                                }
                                        s.setCount(count + 1);
                                        // msg when price going under what you bought
                                        for (int j = 0; j < list.size(); j++) {
                                            if (list.get(j).equals(item.getSymbol())) {
                                                DatabaseReference sPrice = db.child(String.valueOf(userEmail[0])).child("My Coins").child(list.get(j));
                                                sPrice.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        int c = 0;
                                                        String StringValue = (String) snapshot.child("Value").getValue();
                                                        double doubleValue = Double.valueOf(StringValue);
                                                        // when the price 5% under what you bought
                                                        if (doubleValue - (doubleValue * percent) < price) {

                                                            for (int k = 0; k < alreadygotmsg.size(); k++) {
                                                                if (alreadygotmsg.get(k).equals(item.getSymbol())) {
                                                                    c = 1;
                                                                }
                                                            }

                                                            if (c == 0) {
                                                                showNotificationOnDown(item.getName());
                                                                alreadygotmsg.add(item.getSymbol());
                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


                                            }
                                        }

                                        // msg when price going Precent up
                                        if (array[0] * percent + array[0] <= array[4] && item.getCount() == 5) {
                                            showNotificationOnUp(item.getName());
                                            for (int j = 0; j < array.length; j++) {
                                                System.out.println(j + "+" + array[j] + "+" + item.getName());

                                            }
                                            item.setCount(0);

                                        }


                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Homepage.this, "Fail to get the data", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(Homepage.this, "Fail to update", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("X-CMC_PRO_API_KEY", "5110d08d-0bbc-42a5-84b2-bbf3500df227");
                        return headers;
                    }
                };
                requestQueue.add(jsonObjectRequest);


                Toast.makeText(Homepage.this, "times",
                        Toast.LENGTH_SHORT).show();
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }


    public void showNotificationOnUp(String coinName) {
        createNotificationChannel();


        Intent mainIntent = new Intent(this, Homepage.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPIntent = PendingIntent.getActivities(this, 0, new Intent[]{mainIntent}, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder.setSmallIcon(R.drawable.logol);

        builder.setContentTitle("LiranCoins");

        builder.setContentText(coinName + " increased " + s.getPercent() + " in the last " + s.getTime());

//        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder.setAutoCancel(true);

        builder.setContentIntent(mainPIntent);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(Integer_Number, builder.build());
        Integer_Number++;
    }


    public void showNotificationOnDown(String coinName) {
        createNotificationChannel();



        Intent mainIntent = new Intent(this, Homepage.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPIntent = PendingIntent.getActivities(this, 20, new Intent[]{mainIntent}, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);


        builder.setSmallIcon(R.drawable.logol);

        builder.setContentTitle("LiranCoins");

        builder.setContentText("Your " + coinName + " decreased " + s.getPercent() + " of the amount you bought it!");

//        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder.setAutoCancel(true);


        builder.setContentIntent(mainPIntent);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(Integer_Number, builder.build());
        Integer_Number++;
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            CharSequence name="My Notification";
            String descrition="My notification description";

            int importance=NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(descrition);

            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}