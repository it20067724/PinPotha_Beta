package com.app.pinpotha_beta.ui.bottom_bar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.pinpotha_beta.R;
import com.app.pinpotha_beta.ui.records.AddRecord;
import com.app.pinpotha_beta.util.localeHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Translate extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    BottomAppBar bottomAppBar;
    FloatingActionButton faButton;
    RadioGroup langRadio;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        // Assign variable
        bottomAppBar=findViewById(R.id.bottomAppBar);
        faButton=findViewById(R.id.fActionbtn);
        langRadio = findViewById(R.id.langRadioGroup);
        setCheckedBtn();

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


        // Initialize sign in client
        googleSignInClient= GoogleSignIn.getClient(Translate.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

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

    @SuppressLint("NonConstantResourceId")
    public void changeLanguage(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.lang_en:
                if (checked) {
                    localeHelper.setLocale(this, "en");
                    setCheckedBtn();
                    recreate();
                    break;
                }
            case R.id.lang_si:
                if (checked) {
                    localeHelper.setLocale(this, "si");
                    setCheckedBtn();
                    recreate();
                    break;
                }
            case R.id.lang_tm:
                if (checked) {
                    localeHelper.setLocale(this, "ta");
                    setCheckedBtn();
                    recreate();
                    break;
                }

        }
    }

    public void setCheckedBtn() {
        String lang = localeHelper.loadSelectedLocale(this);
        Log.d("workflow", lang);
        if (lang.equals("en")) {
            radioButton = findViewById(R.id.lang_en);
            radioButton.setChecked(true);
        }
        if (lang.equals("si")) {
            radioButton = findViewById(R.id.lang_si);
            radioButton.setChecked(true);
        }
        if (lang.equals("ta")) {
            radioButton = findViewById(R.id.lang_tm);
            radioButton.setChecked(true);
        }
    }
}