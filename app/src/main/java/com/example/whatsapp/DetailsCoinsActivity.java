package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.platforminfo.DefaultUserAgentPublisher;

import java.text.DecimalFormat;

public class DetailsCoinsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView detailImageView;
    private TextView detailSymbolTextView,detailPriceTextView,detailNameTextView;
    private static DecimalFormat df1 = new DecimalFormat("#.####");
    private WebView detaillChartWebView;
    private AppCompatButton button,button1,button2,button3,button4,button5;
    private ImageButton backStackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_coin);

        detailImageView=findViewById(R.id.detailImageView);
        detailSymbolTextView=findViewById(R.id.detailSymbolTextView);
        detailPriceTextView=findViewById(R.id.detailPriceTextView);
        detailNameTextView=findViewById(R.id.detailNameTextView);

        detaillChartWebView=(WebView) findViewById(R.id.detaillChartWebView);
        WebSettings webSettings = detaillChartWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        backStackButton=findViewById(R.id.backStackButton);

        detaillChartWebView.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+getIntent().getStringExtra("symbol")+"USD&interval=1D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");

        button=findViewById(R.id.button);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        backStackButton.setOnClickListener(this);








        String url=getIntent().getStringExtra("logo");


        setImageVieww(url);
        detailSymbolTextView.setText(getIntent().getStringExtra("symbol"));
        detailNameTextView.setText(getIntent().getStringExtra("name"));
        double price=getIntent().getDoubleExtra("rateTV",0.00);
        detailPriceTextView.setText("$"+df1.format(price));
    }

    private void setImageVieww(String url) {
        Glide.with(this).load(url).into(detailImageView);
    }






    @Override
    public void onClick(View v) {
        button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
        switch(v.getId()){
            case R.id.backStackButton:
                Intent intent= new Intent(this,Homepage.class);
                startActivity(intent);
            case R.id.button:
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#363636")));
                detaillChartWebView.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+getIntent().getStringExtra("symbol")+"USD&interval=M&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                break;
            case R.id.button1:
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#363636")));
                detaillChartWebView.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+getIntent().getStringExtra("symbol")+"USD&interval=W&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                break;
            case R.id.button2:
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#363636")));
                detaillChartWebView.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+getIntent().getStringExtra("symbol")+"USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                break;
            case R.id.button3:
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#363636")));
                detaillChartWebView.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+getIntent().getStringExtra("symbol")+"USD&interval=4H&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                break;
            case R.id.button4:
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#363636")));
                detaillChartWebView.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+getIntent().getStringExtra("symbol")+"USD&interval=1H&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));

                break;
            case R.id.button5:
                button5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#363636")));
                detaillChartWebView.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol="+getIntent().getStringExtra("symbol")+"USD&interval=15&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT");
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                button4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));

                break;
        }
    }
}