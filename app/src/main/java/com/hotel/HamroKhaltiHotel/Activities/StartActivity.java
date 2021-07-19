package com.hotel.HamroKhaltiHotel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.hotel.HamroKhaltiHotel.Fragments.ContactUsFragment;
import com.hotel.HamroKhaltiHotel.Fragments.HomeFragment;
import com.hotel.HamroKhaltiHotel.Fragments.HotelFragment;
import com.hotel.HamroKhaltiHotel.Fragments.ReservationFragment;
import com.hotel.HamroKhaltiHotel.R;

public class StartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar1);
        navigationView = findViewById(R.id.navMenu);
        navigationView.setNavigationItemSelectedListener(this);
        View navView = navigationView.getHeaderView(0);
        setSupportActionBar(toolbar);


        actionBarDrawerToggle = new ActionBarDrawerToggle(StartActivity.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
               // Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.hotel:
                //Toast.makeText(this, "Hotel Clicked", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HotelFragment()).addToBackStack(null).commit();

                break;
//            case R.id.reservation:
//                //Toast.makeText(this, "Reservation Clicked", Toast.LENGTH_SHORT).show();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReservationFragment()).addToBackStack(null).commit();
//
//                break;
            case R.id.contactUs:
                //Toast.makeText(this, "Contact us Clicked", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactUsFragment()).addToBackStack(null).commit();

                break;
            case R.id.logOut:
                //Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ()).addToBackStack(null).commit();
                SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent intoLogin=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intoLogin);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


}