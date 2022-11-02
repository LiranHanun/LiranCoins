package com.example.whatsapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.lifecycle.viewmodel.CreationExtras;

import android.content.Intent;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;


    FirebaseAuth mAuth;


    @Override
    public void setContentView(View view) {
        drawerLayout=(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);

        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);

        super.setContentView(drawerLayout);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar=drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        View headerView=navigationView.getHeaderView(0);
        TextView textViewEmail =(TextView) headerView.findViewById(R.id.textViewEmail);
        if(user!=null){
            String email=user.getEmail();
            textViewEmail.setText(email);
        }



        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open,R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer((GravityCompat.START));

        switch (item.getItemId()){
            case R.id.BuyCoinsMenu:
                startActivity(new Intent(this, BuyCoinsActivity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.SellCoinsMenu:
                startActivity(new Intent(this,SellCoinsAcivity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.HomePageMenu:
                startActivity(new Intent(this,Homepage.class));
                overridePendingTransition(0,0);
                break;

            case R.id.SettingMenu:
                startActivity(new Intent(this,SettingsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.MyAccountMenu:
                startActivity(new Intent(this,ActivityMyAccount.class));
                overridePendingTransition(0,0);
                break;
            case R.id.LogoutMenu:
                    mAuth.signOut();
                    signOutUser();

                break;
        }

        return false;
    }

    private void signOutUser() {
        Intent mainActivity = new Intent(this,MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
        finish();
    }


    public void allocateActivityTittle(String titleString){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(titleString);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}