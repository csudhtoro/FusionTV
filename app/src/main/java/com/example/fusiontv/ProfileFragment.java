package com.example.fusiontv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fusiontv.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FirebaseUser currUser;
    private DatabaseReference dbReference;
    private String uId;

    private Button signInSignOut;
    BottomNavigationView bottomNavigationView;
    TextView userEmail;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        signInSignOut = (Button) getView().findViewById(R.id.signInSignOutButton);
        userEmail = (TextView) getView().findViewById(R.id.userEmail);

        if(checkUserLoggedIn(currUser)) {
            //get user and userId from db
            dbReference = FirebaseDatabase.getInstance().getReference("Users");
            uId = currUser.getUid();


            dbReference.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if(userProfile != null) {
                        String fullName = userProfile.fullname;
                        String email = userProfile.email;

                        //set textview from db values
                        userEmail.setText(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            });

            signInSignOut.setText("Sign Out");
            signInSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getContext(), "Signed Out!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            });
        }
        else {
            userEmail.setVisibility(View.GONE);
            signInSignOut.setText("Sign Up/In");
            signInSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            });
        }
    }

    private boolean checkUserLoggedIn(FirebaseUser user) {
        if(currUser != null) return true;
        return false;
    }
}