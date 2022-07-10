package com.app.pinpotha_beta.ui.side_bar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.pinpotha_beta.R;
import com.app.pinpotha_beta.ui.ketayam.NetworkChangeListener;
import com.app.pinpotha_beta.ui.records.AddRecord;
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

public class Help extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    BottomAppBar bottomAppBar;
    FloatingActionButton faButton;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    ImageButton arrow1,arrow2,arrow3,arrow4;
    CardView cardView1,chat,email,cardView2,cardView3,cardView4;
    LinearLayout hiddenView1,hiddenView2,hiddenView3,hiddenView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Assign variable
        bottomAppBar = findViewById(R.id.bottomAppBar);
        faButton = findViewById(R.id.fActionbtn);
        arrow1 = findViewById(R.id.arrow_button1);
        arrow2 = findViewById(R.id.arrow_button2);
        arrow3 = findViewById(R.id.arrow_button3);
        arrow4 = findViewById(R.id.arrow_button4);
        hiddenView1 = findViewById(R.id.hidden_view1);
        hiddenView2 = findViewById(R.id.hidden_view2);
        hiddenView3 = findViewById(R.id.hidden_view3);
        hiddenView4 = findViewById(R.id.hidden_view4);
        cardView1 = findViewById(R.id.base_cardview1);
        cardView2 = findViewById(R.id.base_cardview2);
        cardView3 = findViewById(R.id.base_cardview3);
        cardView4 = findViewById(R.id.base_cardview4);
        chat= findViewById(R.id.chat);
        email= findViewById(R.id.email);

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Check condition
        if (firebaseUser != null) {
            //load details
        } else {
            Toast.makeText(getApplicationContext(), "User Not Avialable", Toast.LENGTH_SHORT).show();
        }


        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(Help.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext()
                        , AddRecord.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(0, 0);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+940777178362" + "&text=Hi"));
                    startActivity(intent);
               }else {
                    Toast.makeText(Help.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
               }

       /*
                Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi");
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setType("text/plain"); startActivity(sendIntent);
 */

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {"thilangavinda@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Query Related to Pinpotha App");
             //   intent.putExtra(Intent.EXTRA_TEXT, body.getText().toString());
            //
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(Help.this, "There is no application that support this action",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        bottomAppBar.setOnMenuItemClickListener(menuItem -> {
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

        arrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hiddenView1.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(cardView1,
                            new AutoTransition());
                    hiddenView1.setVisibility(View.GONE);
                    arrow1.setImageResource(R.drawable.expandmore);
                }

                else {

                    TransitionManager.beginDelayedTransition(cardView1,
                            new AutoTransition());
                    hiddenView1.setVisibility(View.VISIBLE);
                    arrow1.setImageResource(R.drawable.expandless);
                }
            }
        });

        arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hiddenView2.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(cardView1,
                            new AutoTransition());
                    hiddenView2.setVisibility(View.GONE);
                    arrow2.setImageResource(R.drawable.expandmore);
                }

                else {

                    TransitionManager.beginDelayedTransition(cardView2,
                            new AutoTransition());
                    hiddenView2.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.drawable.expandless);
                }
            }
        });

        arrow3.setOnClickListener(view -> {

            if (hiddenView3.getVisibility() == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(cardView3,
                        new AutoTransition());
                hiddenView3.setVisibility(View.GONE);
                arrow3.setImageResource(R.drawable.expandmore);
            }

            else {

                TransitionManager.beginDelayedTransition(cardView3,
                        new AutoTransition());
                hiddenView3.setVisibility(View.VISIBLE);
                arrow3.setImageResource(R.drawable.expandless);
            }
        });

        arrow4.setOnClickListener(view -> {

            if (hiddenView4.getVisibility() == View.VISIBLE) {

                TransitionManager.beginDelayedTransition(cardView4,
                        new AutoTransition());
                hiddenView4.setVisibility(View.GONE);
                arrow4.setImageResource(R.drawable.expandmore);
            }

            else {

                TransitionManager.beginDelayedTransition(cardView4,
                        new AutoTransition());
                hiddenView4.setVisibility(View.VISIBLE);
                arrow4.setImageResource(R.drawable.expandless);
            }
        });

    }



    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {

            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            Log.d("whatsapp","error:"+e.getMessage());
            app_installed = false;
        }
        return app_installed;
    }
}