package com.app.pinpotha_beta.ui.records;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.pinpotha_beta.R;
import com.app.pinpotha_beta.ui.bottom_bar.ProfileActivity;
import com.app.pinpotha_beta.ui.bottom_bar.Search;
import com.app.pinpotha_beta.ui.bottom_bar.SideMenu;
import com.app.pinpotha_beta.ui.bottom_bar.Translate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewRecord extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    BottomAppBar bottomAppBar;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    FloatingActionButton faButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        faButton=findViewById(R.id.fActionbtn);
        bottomAppBar=findViewById(R.id.bottomAppBar);
        recyclerView=findViewById(R.id.recyclerview);
        db=FirebaseFirestore.getInstance();

        // Initialize firebase auth
        firebaseAuth=FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        // Check condition
        if(firebaseUser!=null)
        {
            //load details
        }
        else{
            Toast.makeText(getApplicationContext(), "User Not Avialable", Toast.LENGTH_SHORT).show();
        }
        googleSignInClient= GoogleSignIn.getClient(ViewRecord.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext()
                        , AddRecord.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(0, 0);
            }
        });

        bottomAppBar.setOnMenuItemClickListener(menuItem->{
            switch (menuItem.getItemId()) {
                case R.id.sidemenu:
                    startActivity(new Intent(getApplicationContext()
                            , SideMenu.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.translatelam:
                    startActivity(new Intent(getApplicationContext()
                            , Translate.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.home:
                    startActivity(new Intent(getApplicationContext()
                            , ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.search:
                    startActivity(new Intent(getApplicationContext()
                            , Search.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }





}