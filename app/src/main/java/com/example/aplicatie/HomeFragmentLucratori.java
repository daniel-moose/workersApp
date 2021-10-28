package com.example.aplicatie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomeFragmentLucratori extends Fragment {
    FirebaseAuth firebaseAuth;
    private TextView profileFirstName, profileJob, profileLastName,profileStatus;     //initializare text views pentru a afisa datele de profil
    private FirebaseDatabase firebaseDatabase;
    String job;
    Button setOnline, setOffline,butonComenzi, addServices;
    String status;
    private static final String USER = "Users";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_homelucratori,container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        butonComenzi = v.findViewById(R.id.butonComenzi);
        profileFirstName = v.findViewById(R.id.tvFirstNameLucra);
        profileLastName = v.findViewById(R.id.tvSecondNameLucra);
        profileJob =  v.findViewById(R.id.tvUserJobLucra);
        profileStatus = v.findViewById(R.id.userStatus);
        addServices = v.findViewById(R.id.addServicesButton);
        addServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getContext(),AdaugareServicii.class));
            }
        });


        final DatabaseReference databaseReference = firebaseDatabase.getReference(USER).child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                String inConversation = dataSnapshot.child("inConversation").getValue().toString();
                job = userProfile.getUserJob();
                profileFirstName.setText("First Name: "+userProfile.getUserFirstName()); //Afisarea primului nume al utilizatorului pe pagina Home a aplicatiei
                profileLastName.setText("Last Name: "+userProfile.getUserLastName());  //Afisarea prenumelui utilizatorului pe pagina Home a aplicatiei
                status = userProfile.getUserStatus();  //Preluarea statusului utilizatorului
                profileStatus.setText("Status:"+status);  //Afisarea statusului utilizatorului pe pagina Home a aplicatiei

                // Afisarea butoanelor de setare Online respectiv Offline
                if(status.equals("Online")){
                    setOffline.setVisibility(View.VISIBLE);
                    setOnline.setVisibility(View.INVISIBLE);
                }
                else if (status.equals("Offline")){
                    setOnline.setVisibility(View.VISIBLE);
                    setOffline.setVisibility(View.INVISIBLE);
                }
                if(inConversation.equals("")){
                    butonComenzi.setVisibility(View.GONE);
                }
                else{
                    butonComenzi.setVisibility(View.VISIBLE);
                }
                butonComenzi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(),activitateConversatie.class));
                    }
                });

                //Afisarea job-ului utilizatorului in fereastra Home a aplicatiei
                if(job != null) {
                    profileJob.setText("Job: " + job);

                }
                else{
                    profileJob.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        //Initializare buton pentru setarea statusului Online a user-ului lucrator
        setOnline = v.findViewById(R.id.setOnline);

        setOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statusonline = "Online";
                HashMap hashMap = new HashMap();
                hashMap.put("userStatus",statusonline);
                databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getContext(),"You are now On the line!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Initializare buton pentru setarea statusului Offline a user-ului lucrator
        setOffline = v.findViewById(R.id.setOffline);

        setOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statusoffline = "Offline";
                HashMap hashMap = new HashMap();
                hashMap.put("userStatus",statusoffline);
                databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getContext(),"You are now Off the line!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return  v;

    }


}
