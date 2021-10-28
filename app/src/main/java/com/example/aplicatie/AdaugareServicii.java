package com.example.aplicatie;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdaugareServicii extends AppCompatActivity {
    private EditText serviceName,servicePrice;
    private Button addService;
    private String user,numeServiciu,pretServiciu,tipServiciu;
    private Spinner spinnerTipServicii;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_servicii);
        serviceName = (EditText) findViewById(R.id.numeServiciu);
        servicePrice=(EditText)findViewById(R.id.pretServiciu);
        addService = (Button)findViewById(R.id.butonAdaugareServiciu);
        spinnerTipServicii = (Spinner)findViewById(R.id.spinnerTipServicii);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(user).child("userServices");
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeServiciu = serviceName.getText().toString();
                pretServiciu = servicePrice.getText().toString();
                tipServiciu = spinnerTipServicii.getSelectedItem().toString();
                final Serviciu profilServiciu = new Serviciu(numeServiciu,pretServiciu,tipServiciu);
                if (numeServiciu.isEmpty() || pretServiciu.isEmpty()){
                    Toast.makeText(AdaugareServicii.this,"Adaugati toate detaliile!",Toast.LENGTH_LONG).show();
                }
                else{
                    databaseReference.child(numeServiciu).setValue(profilServiciu);
                    serviceName.setText("");
                    servicePrice.setText("");

                }
            }
        });
    }
}