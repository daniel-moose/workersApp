package com.example.aplicatie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchFragment extends Fragment {
    private Spinner searchspinner;
    private Button searchButton;
    private Button buttonSearch;
    private RecyclerView searchlist;
    private DatabaseReference databaseReference,databaseReference1;
    private FirebaseAuth firebaseAuth;
    private static final String USER = "Users";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_search,container, false);
        searchspinner = (Spinner) v.findViewById(R.id.spinner1);
        searchButton = (Button) v.findViewById(R.id.searchButton);
        searchlist = (RecyclerView) v.findViewById(R.id.searchList);
        searchlist.setHasFixedSize(true);
        searchlist.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference= FirebaseDatabase.getInstance().getReference(USER);
        databaseReference1= FirebaseDatabase.getInstance().getReference("Convesations");
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = searchspinner.getSelectedItem().toString();

                firebaseUserSearch(searchText);
            }
        });

        return v;
    }

    private void firebaseUserSearch(String searchText) {

        Query firebaseSearchQuery1 = databaseReference.orderByChild("userJob").startAt(searchText).endAt(searchText+"\uf8ff");
        Toast.makeText(getContext(),"Search started!",Toast.LENGTH_SHORT).show();


        FirebaseRecyclerAdapter<UserProfile, UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserProfile, UserViewHolder>(
                UserProfile.class,
                R.layout.list_layout,
                UserViewHolder.class,
                firebaseSearchQuery1

        ) {
            @Override
            protected void populateViewHolder(UserViewHolder userViewHolder, UserProfile userProfile, int i) {
                userViewHolder.setProfile(getContext(), userProfile.getUserFirstName(), userProfile.getUserLastName(), userProfile.getUserJob(), userProfile.getUserStatus(), userProfile.getUserID());
            }
        };
        searchlist.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        View mView;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Conversations");

        public UserViewHolder(@NonNull final View itemView) {
            super(itemView);
            mView= itemView;
        }
        public void setProfile(final Context ctx, String userfirstname, String userlastname, String userjob, String userstatus, final String userid){
            TextView user_firstname = (TextView) mView.findViewById(R.id.searchFirstName);
            TextView user_lastname = (TextView) mView.findViewById(R.id.searchLastName);
            TextView user_job = (TextView) mView.findViewById(R.id.searchJob);
            TextView user_status = (TextView) mView.findViewById(R.id.searchStatus);
            Button buttonSearch = (Button) mView.findViewById(R.id.buttonSearch);
            final FirebaseAuth firebaseAuth;
            firebaseAuth = FirebaseAuth.getInstance();

            user_firstname.setText(userfirstname);
            user_lastname.setText(userlastname);
            user_job.setText(userjob);
            user_status.setText(userstatus);

                if(userstatus.equals("Offline")){
                    buttonSearch.setVisibility(View.GONE);
                    user_firstname.setVisibility(View.GONE);
                    user_job.setVisibility(View.GONE);
                    user_lastname.setVisibility(View.GONE);
                    user_status.setVisibility(View.GONE);

                }
                else if(userstatus.equals("Online")){
                    buttonSearch.setVisibility(View.VISIBLE);
                }
            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("searchingWith").setValue(userid);
//                    databaseReference2.child(userid + firebaseAuth.getCurrentUser().getUid()).child("mesajBeneficiar").setValue("");
//                    databaseReference2.child(userid + firebaseAuth.getCurrentUser().getUid()).child("mesajOferitor").setValue("");
                    ctx.startActivity(new Intent(ctx,activitateServicii.class));
                }
            });

        }



    }
}
