package com.example.aplicatie;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class paginaPrincipalaLucratori extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
private Button logout;
private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principala_lucratori);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My notification","My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String currentCommand,acceptedCommand,refusedCommand,inCommand;
                currentCommand = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("currentCommand").getValue().toString();
                inCommand = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("inCommand").getValue().toString();
                String message = "Aveti o noua comanda!";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        paginaPrincipalaLucratori.this,"My notification").setSmallIcon(R.drawable.ic_baseline_looks_24).setContentTitle("Comanda noua!").setContentText(message).setAutoCancel(true);

                Intent intent = new Intent(paginaPrincipalaLucratori.this,activitateAcceptareComanda.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(paginaPrincipalaLucratori.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setVibrate(new long[]{1000,1000});
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(paginaPrincipalaLucratori.this);
                if(currentCommand.equals("")){

                }
                else{
                    acceptedCommand = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(currentCommand).child("acceptedCommand").getValue().toString();
                    refusedCommand = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(currentCommand).child("refusedCommand").getValue().toString();
                    if(acceptedCommand.equals("0")&&refusedCommand.equals("0")){
                        managerCompat.notify(0, builder.build());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        logout = (Button)findViewById(R.id.btnLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).child("userStatus").setValue("Offline");
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(paginaPrincipalaLucratori.this,MainActivity.class));
            }
        });
        //logout.setVisibility(View.GONE);

        BottomNavigationView bottomNav = (BottomNavigationView)findViewById(R.id.bottom_navigationLucratori);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLucratori,new HomeFragmentLucratori()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = new HomeFragmentLucratori();
            switch (item.getItemId()){
                case R.id.nav_homeLucratori:
                    selectedFragment = new HomeFragmentLucratori();
                    logout.setVisibility(View.VISIBLE);
                    break;
                case R.id.nav_favoritesLucratori:
                    selectedFragment = new ComenziFragmentLucratori();
                    //logout.setVisibility(View.GONE);
                    break;
                case R.id.nav_searchLucratori:
                    selectedFragment = new SearchFragmentLucratori();
                    logout.setVisibility(View.GONE);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLucratori, selectedFragment).commit();

            return true;
        }
    };

}
