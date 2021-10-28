package com.example.aplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class activitateAcceptareComanda extends AppCompatActivity {
    Button butonAcceptareComanda,butonRefuzareComanda;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitate_acceptare_comanda);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        butonAcceptareComanda = (Button) findViewById(R.id.butonAcceptareComanda);
        butonRefuzareComanda = (Button) findViewById(R.id.butonRefuzareComanda);
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
        butonAcceptareComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String currentCommand;
                        currentCommand = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("currentCommand").getValue().toString();
                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(currentCommand).child("acceptedCommand").setValue("1");
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                startActivity(new Intent(activitateAcceptareComanda.this,paginaPrincipalaLucratori.class));
                finish();
            }
        });
        butonRefuzareComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String currentCommand;
                        currentCommand = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("currentCommand").getValue().toString();
                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(currentCommand).child("refusedCommand").setValue("1");
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                startActivity(new Intent(activitateAcceptareComanda.this,paginaPrincipalaLucratori.class));
                finish();
            }
        });
    }
}