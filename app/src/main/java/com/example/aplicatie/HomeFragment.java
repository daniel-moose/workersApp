package com.example.aplicatie;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    private TextView profileFirstName, profileJob, profileLastName;     //initializare text views pentru a afisa datele de profil
    private FirebaseDatabase firebaseDatabase; //initializare text views pentru a afisa datele de profil
    String job;
    DatabaseReference databaseReference;
    Button logOut;
    private static final String USER = "Users";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v =  inflater.inflate(R.layout.fragment_home,container, false);

       firebaseAuth = FirebaseAuth.getInstance();
       firebaseDatabase = FirebaseDatabase.getInstance();

        profileFirstName = v.findViewById(R.id.tvFirstName);
        profileLastName = v.findViewById(R.id.tvSecondName);
        profileJob =  v.findViewById(R.id.tvUserJob);


        databaseReference = FirebaseDatabase.getInstance().getReference(USER).child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                job = userProfile.getUserJob();
                profileFirstName.setText("First Name: "+userProfile.getUserFirstName());
                profileLastName.setText("Last Name: "+userProfile.getUserLastName());
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



        return  v;

    }


}
