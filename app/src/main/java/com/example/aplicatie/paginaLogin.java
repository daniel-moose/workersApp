package com.example.aplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class paginaLogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText userFirstName, userLastName, userEmail, userPassword, userPhone;
    private Button regButton;
    private FirebaseAuth firebaseAuth;
    private Switch switchSpinner;
    private Spinner spinner1;
    String userID,firstname,lastname,email,phonenumber,job,password,userstatus,inConversation;
    String switchnotchecked = null;
    String switchischecked;
    private static final String USER = "Users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_login);

        spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Meserii, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        setupUIViews();


        switchSpinner = (Switch)findViewById(R.id.switch1);


        switchSpinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spinner1.setVisibility(View.VISIBLE);
                }
                else{
                    spinner1.setVisibility(View.INVISIBLE);
                }
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Se introduc datele in baza de date
                    String user_Email = userEmail.getText().toString().trim();
                    String user_Password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_Email,user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                //Toast.makeText(paginaLogin.this, "Registration successful! An email was sent to you for verification!", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(paginaLogin.this,MainActivity.class));
                                sendEmailVerification();
                            }
                            else{
                                Toast.makeText(paginaLogin.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
    private void setupUIViews(){
        userFirstName = (EditText)findViewById(R.id.editText4);
        userLastName = (EditText)findViewById(R.id.editText5);
        userEmail = (EditText)findViewById(R.id.editText3);
        userPassword = (EditText)findViewById(R.id.editText6);
        userPhone = (EditText)findViewById(R.id.editText7);
        regButton = (Button)findViewById(R.id.buttonReg);
    }
    private Boolean validate(){

        Boolean result = false;

        firstname = userFirstName.getText().toString();
        lastname = userLastName.getText().toString();
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        phonenumber = userPhone.getText().toString();
        inConversation = "";

        if(switchSpinner.isChecked()){
            job = spinner1.getSelectedItem().toString();
            userstatus = "Offline";
        }
        else{
            job = switchnotchecked;
            userstatus = null;
        }



        if(firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || phonenumber.isEmpty()){
            Toast.makeText(paginaLogin.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
     return result;
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(paginaLogin.this,"Successfully Registered!An email was sent to you for validation!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(paginaLogin.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(paginaLogin.this, "Verification mail wasn't sent to you!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        UserProfile userProfile = new UserProfile(firstname,lastname,email,phonenumber,job,password,userstatus,userID,inConversation);
        DatabaseReference myRef = firebaseDatabase.getReference(USER).child(firebaseAuth.getUid());
            myRef.setValue(userProfile);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
