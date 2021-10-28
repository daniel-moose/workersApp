package com.example.aplicatie;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class activitateConversatie extends AppCompatActivity {
    private DatabaseReference databaseReference1,databaseReference2,databaseReference4;
    private FirebaseAuth firebaseAuth;
    private TextView textMesajBeneficiar,textMesajOferator,idConv,userJob;
    private Button butonSend;
    private EditText campTrimitereMesaj;
    public String idConver;
    RecyclerView recyclerView;
    mesajeAdapter mesajeAdapter;
    ArrayList<Mesaje> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitate_conversatie);
        textMesajBeneficiar = findViewById(R.id.textMesajBeneficiar);
        textMesajOferator = findViewById(R.id.textMesajOferator);
        butonSend = findViewById(R.id.butonSend);
        idConv = findViewById(R.id.idConv);
        userJob = findViewById(R.id.userJobb);
        campTrimitereMesaj = findViewById(R.id.campTrimitereText);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.mesajeTrimise);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        mesajeAdapter = new mesajeAdapter(this,list);
        recyclerView.setAdapter(mesajeAdapter);
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference4 = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                final String job = userProfile.getUserJob();
                idConver = snapshot.child("inConversation").getValue().toString();
                idConv.setText(idConver);
                userJob.setText(job);
                final DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Conversations").child(idConver);
                butonSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mesaj = campTrimitereMesaj.getText().toString();
                        if(mesaj.isEmpty()){
                            Toast.makeText(activitateConversatie.this,"Please eneter your message!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        campTrimitereMesaj.setText("");
                        final Mesaje mesaje = new Mesaje(firebaseAuth.getUid(),mesaj);
                        databaseReference3.child("trimitereMesaj").push().setValue(mesaje).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                databaseReference3.child("primireMesaj").push().setValue(mesaje);
                            }
                        });
                    }
                });

                databaseReference2 = FirebaseDatabase.getInstance().getReference("Conversations").child(idConver).child("trimitereMesaj");
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Mesaje mesaje = dataSnapshot.getValue(Mesaje.class);
                            list.add(mesaje);
                        }
                        mesajeAdapter.notifyDataSetChanged();

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