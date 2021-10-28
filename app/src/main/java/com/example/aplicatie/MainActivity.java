package com.example.aplicatie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public TextView textview;
    private EditText Name;
    private EditText Password;
    private Button Login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    String job;
    String check;
    private static final String USER = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView = (TextView) findViewById(R.id.text);
        TextView textView1 = (TextView)findViewById(R.id.textForgotPassword);
        textView = (TextView) findViewById(R.id.text);
        textView.setPaintFlags(textView.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        textView1.setPaintFlags(textView.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        textView1 = (TextView) findViewById(R.id.textForgotPassword);
        Login = (Button)findViewById(R.id.button);
        Name = (EditText)findViewById(R.id.editText);
        Password = (EditText)findViewById(R.id.editText2);


        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        FirebaseUser user = firebaseAuth.getCurrentUser();

       // if(user != null){
           // checkUserJob();
          //  finish();
        //}

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,paginaLogin.class);
                startActivity(intent);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,resetareParola.class);
                startActivity(intent);
            }
        });

    }
    private void validate(final String userName, final String userPassword){
        //progressDialog.setMessage("Hello!");
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        //progressDialog.show();
                        //Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(MainActivity.this, paginaPrincipala.class));
                        checkEmail();
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Login failed!Wrong Password or Email", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
    private void checkEmail(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            checkUserJob();

        }
        else{
            Toast.makeText(this, "Verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
    private void checkUserJob(){
        DatabaseReference databaseReference = firebaseDatabase.getReference(USER).child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                job = userProfile.getUserJob();
                if(job != null) {
                    startActivity(new Intent(MainActivity.this, paginaPrincipalaLucratori.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this, paginaPrincipala.class));
                }
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
