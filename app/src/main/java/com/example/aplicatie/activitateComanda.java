package com.example.aplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class activitateComanda extends AppCompatActivity {
    Button butonComandaServiciu;
    TextView numeServiciuComanda,pretServiciuComanda;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitate_comanda);
        final String currentUser;
        butonComandaServiciu = (Button)findViewById(R.id.butonComandaServiciu);
        numeServiciuComanda = (TextView)findViewById(R.id.numeServiciuComanda);
        pretServiciuComanda = (TextView)findViewById(R.id.pretServiciuComanda);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String commandFor,numeServiciu,pretServiciu,uID;
                uID = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("inCommandWith").getValue().toString();
                commandFor = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("commandFor").getValue().toString();
                numeServiciu = snapshot.child(uID).child("userServices").child(commandFor).child("serviceName").getValue().toString();
                pretServiciu = snapshot.child(uID).child("userServices").child(commandFor).child("servicePrice").getValue().toString();
                numeServiciuComanda.setText(numeServiciu);
                pretServiciuComanda.setText(pretServiciu);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        butonComandaServiciu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String commandFor,numeServiciu,pretServiciu,uID,idComanda,comandaRefuzata,comandaAcceptata,comandaCompletata,idBeneficiar,idOferitor;
                        uID = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("inCommandWith").getValue().toString();
                        commandFor = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("commandFor").getValue().toString();
                        idComanda = snapshot.child(firebaseAuth.getCurrentUser().getUid()).child("currentCommand").getValue().toString();
                        numeServiciu = snapshot.child(uID).child("userServices").child(commandFor).child("serviceName").getValue().toString();
                        pretServiciu = snapshot.child(uID).child("userServices").child(commandFor).child("servicePrice").getValue().toString();
                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Users");
                        comandaRefuzata = "0";
                        comandaAcceptata = "0";
                        comandaCompletata = "0";
                        idBeneficiar = snapshot.child(currentUser).child("userID").getValue().toString();
                        idOferitor = snapshot.child(uID).child("userID").getValue().toString();
                        Comanda comanda = new Comanda(numeServiciu,pretServiciu,idComanda,comandaRefuzata,comandaAcceptata,comandaCompletata,idBeneficiar,idOferitor);
                        databaseReference2.child(uID).child("Commands").child(idComanda).setValue(comanda);
                        databaseReference2.child(uID).child("inCommand").setValue("1");
                        databaseReference2.child(uID).child("currentCommand").setValue(idComanda);
                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(idComanda).setValue(comanda);

                        databaseReference1.removeEventListener(this);
//                        databaseReference2.child(uID).child("Commands").child(idComanda).child("serviceName").setValue(numeServiciu);
//                        databaseReference2.child(uID).child("Commands").child(idComanda).child("servicePrice").setValue(pretServiciu);
//                        databaseReference2.child(uID).child("Commands").child(idComanda).child("commandID").setValue(idComanda);
//                        databaseReference2.child(uID).child("Commands").child(idComanda).child("refusedCommand").setValue(comandaRefuzata);
//                        databaseReference2.child(uID).child("Commands").child(idComanda).child("completedCommand").setValue(comandaCompletata);
//                        databaseReference2.child(uID).child("Commands").child(idComanda).child("beneficiaryID").setValue(idBeneficiar);
//                        databaseReference2.child(uID).child("Commands").child(idComanda).child("providerID").setValue(idOferitor);
//
//                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(idComanda).child("serviceName").setValue(numeServiciu);
//                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(idComanda).child("servicePrice").setValue(pretServiciu);
//                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(idComanda).child("commandID").setValue(idComanda);
//                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(idComanda).child("refusedCommand").setValue(comandaRefuzata);
//                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(idComanda).child("completedCommand").setValue(comandaCompletata);
//                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(idComanda).child("beneficiaryID").setValue(idBeneficiar);
//                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).child("Commands").child(idComanda).child("providerID").setValue(idOferitor);


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                startActivity(new Intent(activitateComanda.this,paginaPrincipala.class));
                finish();
            }
        });
    }
}