package com.example.aplicatie;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class serviciiAdapter extends RecyclerView.Adapter<serviciiAdapter.MyViewHolder> {

    Context context;
    ArrayList<Serviciu> list;
    FirebaseAuth firebaseAuth;

    public serviciiAdapter(Context context, ArrayList<Serviciu> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemservicii,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        final Serviciu serviciu = list.get(position);
        String type;
        holder.numeserviciu.setText(serviciu.getServiceName());
        holder.pretserviciu.setText(serviciu.getServicePrice());
        type = serviciu.getServiceType();
        firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        if(type.equals("Doar rezervari")){
            holder.butonComanda.setVisibility(View.GONE);
            holder.butonRezervare.setVisibility(View.VISIBLE);
        }
        if(type.equals("Doar comenzi")){
            holder.butonRezervare.setVisibility(View.GONE);
            holder.butonComanda.setVisibility(View.VISIBLE);
        }
        if(type.equals("Comenzi si rezervari")){
            holder.butonComanda.setVisibility(View.VISIBLE);
            holder.butonRezervare.setVisibility(View.VISIBLE);
        }
        holder.butonComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                      String uID = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("searchingWith").getValue().toString();
                      databaseReference.child(uID).child("inCommandWith").setValue(firebaseAuth.getCurrentUser().getUid());
                      databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("inCommandWith").setValue(uID);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                String idComanda;
                idComanda = String.valueOf(System.currentTimeMillis());
                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("currentCommand").setValue(idComanda);
                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("commandFor").setValue(serviciu.getServiceName());
                context.startActivity(new Intent(context,activitateComanda.class));
            }
        });
        holder.butonRezervare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,activitateRezervare.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView numeserviciu, pretserviciu;
        Button butonComanda,butonRezervare;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            numeserviciu = itemView.findViewById(R.id.servicename);
            pretserviciu = itemView.findViewById(R.id.serviceprice);
            butonComanda = itemView.findViewById(R.id.butonComanda);
            butonRezervare = itemView.findViewById(R.id.butonRezervare);
        }
    }
}
