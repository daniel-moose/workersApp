package com.example.aplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class activitateServicii extends AppCompatActivity {
    private Button butonConversatie,butonAnulare;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    RecyclerView listaServicii;
    serviciiAdapter serviciiAdapter;
    ArrayList<Serviciu> list;

    private String currentuser,userid,uID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitate_servicii);
        butonConversatie = (Button)findViewById(R.id.butonConversatie);
        butonAnulare = (Button)findViewById(R.id.butonAnulare);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser().getUid();
        listaServicii = findViewById(R.id.listaServicii);
        listaServicii.setHasFixedSize(true);
        listaServicii.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        serviciiAdapter = new serviciiAdapter(this,list);
        listaServicii.setAdapter(serviciiAdapter);
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
        final DatabaseReference databaseReference3 = firebaseDatabase.getReference("Users");
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("Users");
        butonConversatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        userid = snapshot.child(currentuser).child("searchingWith").getValue().toString();
                        databaseReference.child(userid).child("inConversation").setValue(userid+currentuser);
                        databaseReference.child(currentuser).child("inConversation").setValue(userid+currentuser);


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                startActivity(new Intent(activitateServicii.this,activitateConversatie.class));
                finish();
            }
        });
        butonAnulare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        uID = snapshot.child(currentuser).child("searchingWith").getValue().toString();
                        databaseReference3.child(uID).child("inConversation").setValue("");
                        databaseReference3.child(currentuser).child("inConversation").setValue("");
                        databaseReference3.child(currentuser).child("searchingWith").setValue("");
                        databaseReference3.removeEventListener(this);

                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

                startActivity(new Intent(activitateServicii.this,paginaPrincipala.class));
                finish();
            }
        });
       databaseReference2.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
           String userID;
           userID = snapshot.child(currentuser).child("searchingWith").getValue().toString();
           DatabaseReference databaseReference1 = firebaseDatabase.getReference("Users").child(userID).child("userServices");
           databaseReference1.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                   list.clear();
                   for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                       Serviciu serviciu = dataSnapshot.getValue(Serviciu.class);
                       list.add(serviciu);
                   }
                   serviciiAdapter.notifyDataSetChanged();
               }

               @Override
               public void onCancelled(@NonNull @NotNull DatabaseError error) {

               }
           });
           }

           @Override
           public void onCancelled(@NonNull @NotNull DatabaseError error) {

           }
       });

    }
}