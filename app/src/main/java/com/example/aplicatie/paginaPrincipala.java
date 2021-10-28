package com.example.aplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class paginaPrincipala extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button logOut;
    FirebaseDatabase firebaseDatabase;


    public paginaPrincipala() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principala);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //Pentru a afisa datele de profil

        //Aici se incheie
        logOut = (Button)findViewById(R.id.button4);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(paginaPrincipala.this,MainActivity.class));
            }
        });
        logOut.setVisibility(View.VISIBLE);

        BottomNavigationView bottomNav = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    logOut.setVisibility(View.VISIBLE);
                    break;
                case R.id.nav_favorites:
                    selectedFragment = new ComenziFragment();
                    logOut.setVisibility(View.GONE);
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    logOut.setVisibility(View.GONE);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

}

