package com.example.aplicatie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class mesajeAdapter extends RecyclerView.Adapter{
    Context context;
    ArrayList<Mesaje> list;
    int mesajtrimis = 1;
    int mesajprimit = 2;

    public mesajeAdapter(Context context, ArrayList<Mesaje> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

       if(viewType==mesajtrimis){
           View v = LayoutInflater.from(context).inflate(R.layout.mesaj_trimis,parent,false);
           return new trimitereMesaj(v);
       }
        else{
           View v = LayoutInflater.from(context).inflate(R.layout.mesaj_primit,parent,false);
           return new primireMesaj(v);
       }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Mesaje mesaje = list.get(position);
        if(holder.getClass()==trimitereMesaj.class){
            trimitereMesaj viewHolder = (trimitereMesaj) holder;
            viewHolder.mesajTrimis.setText(mesaje.getMesaj());
        }
        else{
            primireMesaj viewHolder = (primireMesaj) holder;
            viewHolder.mesajPrimit.setText(mesaje.getMesaj());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        Mesaje mesaje = list.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(mesaje.getIDtrimis())){
            return mesajtrimis;
        }
        else {
            return mesajprimit;
        }

    }

    class trimitereMesaj extends RecyclerView.ViewHolder{
        TextView mesajTrimis;
        public trimitereMesaj(@NonNull @NotNull View itemView) {
            super(itemView);
            mesajTrimis = itemView.findViewById(R.id.mesajTrimis);
        }
    }
    class primireMesaj extends RecyclerView.ViewHolder {
        TextView mesajPrimit;
        public primireMesaj(@NonNull @NotNull View itemView) {
            super(itemView);
            mesajPrimit = itemView.findViewById(R.id.mesajPrimit);
        }
    }
}
