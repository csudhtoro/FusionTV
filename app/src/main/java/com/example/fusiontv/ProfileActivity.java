package com.example.fusiontv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {


    FirebaseUser currUser;
    private DatabaseReference dbReference;
    private String uId;

    private Button signInSignOut;
    BottomNavigationView bottomNavigationView;
    TextView userEmail;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.bottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);


        currUser = FirebaseAuth.getInstance().getCurrentUser();
        signInSignOut = (Button) findViewById(R.id.signInSignOutButton);
        userEmail = (TextView) findViewById(R.id.userEmail);



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
                    Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            });

            signInSignOut.setText("Sign Out");
            signInSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(ProfileActivity.this, "Signed Out!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
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
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                }
            });
        }
    }

    private boolean checkUserLoggedIn(FirebaseUser user) {
        if(currUser != null) return true;
        return false;
    }
}