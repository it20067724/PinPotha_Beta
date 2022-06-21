package com.app.pinpotha_beta.ui.bottom_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.pinpotha_beta.MainActivity;
import com.app.pinpotha_beta.R;
import com.app.pinpotha_beta.ui.ketayam.LoadingDialog;
import com.app.pinpotha_beta.ui.records.AddRecord;
import com.app.pinpotha_beta.ui.side_bar.About;
import com.app.pinpotha_beta.ui.side_bar.Communiuty;
import com.app.pinpotha_beta.ui.side_bar.Help;
import com.app.pinpotha_beta.ui.side_bar.Settings;
import com.app.pinpotha_beta.util.TimeData;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SideMenu extends AppCompatActivity {

    // Initialize variable
    ImageView ivImage;
    TextView displayName,display_email,display_last_login;
    Button btLogout,btSettings,btCommunity,btHelp,btAbout,btIInvFriend;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    BottomAppBar bottomAppBar;
    FloatingActionButton faButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);

        ivImage=findViewById(R.id.iv_image);
        displayName=findViewById(R.id.display_name);
        display_email=findViewById(R.id.display_email);
        display_last_login=findViewById(R.id.display_last_login);
        btLogout=findViewById(R.id.btn_logout);
        btSettings=findViewById(R.id.btn_settings);
        btCommunity=findViewById(R.id.btn_community);
        btHelp=findViewById(R.id.btn_help);
        btAbout=findViewById(R.id.btn_about);
        btIInvFriend=findViewById(R.id.btn_invite);
        bottomAppBar=findViewById(R.id.bottomAppBar);
        faButton=findViewById(R.id.fActionbtn);

        // Initialize firebase auth
        firebaseAuth=FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        // Check condition
        if(firebaseUser!=null)
        {
            // When firebase user is not equal to null
            // Set image on image view
          //  Glide.with(SideMenu.this)
           //         .load(firebaseUser.getPhotoUrl())
            //        .into(ivImage);
            // set name on text view
            Glide.with(SideMenu.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(ivImage);
            displayName.setText(firebaseUser.getDisplayName());
            display_email.setText(firebaseUser.getEmail());
            //
            //long value handle null pointer and display using epoxy converter
           // firebaseUser.getMetadata().getLastSignInTimestamp();
            display_last_login.setText("Last Login : "+TimeData.getEpochTime(firebaseUser.getMetadata().getLastSignInTimestamp()));
            //getUid() returns the unique id at db

        }
        LoadingDialog loadingDialog=new LoadingDialog(SideMenu.this);
        loadingDialog.startLoader();

        googleSignInClient= GoogleSignIn.getClient(SideMenu.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        //logout button onClick
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out from google
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Check condition
                        if(task.isSuccessful())
                        {
                            // When task is successful
                            // Sign out from firebase
                            firebaseAuth.signOut();
                            Intent intent = new Intent(SideMenu.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            // Display Toast
                            Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();

                            // Finish activity
                            finish();
                        }
                    }
                });
            }
        });

        //float buttton on click
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext()
                        , AddRecord.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(0, 0);
            }
        });

        //Settings buttton on click
        btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext()
                        , Settings.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(0, 0);
            }
        });

        btCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext()
                        , Communiuty.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(0, 0);
            }
        });

        btHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext()
                                , Help.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        overridePendingTransition(0, 0);
                    }
                });

        btAbout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext()
                                , About.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        overridePendingTransition(0, 0);
                    }
                });



        bottomAppBar.setOnMenuItemClickListener(menuItem->{
            switch (menuItem.getItemId()) {
                case R.id.sidemenu:
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
        loadingDialog.stopLoader();
    }
    }
