package com.app.pinpotha_beta.ui.bottom_bar;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.app.pinpotha_beta.R;
import com.app.pinpotha_beta.ui.records.AddRecord;
import com.app.pinpotha_beta.ui.ketayam.NetworkChangeListener;

import com.app.pinpotha_beta.ui.records.ViewRecord;
import com.app.pinpotha_beta.util.TimeData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Search extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    BottomAppBar bottomAppBar;
    FloatingActionButton faButton;
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    TextInputEditText recStartDate, recEndDate;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Assign variable
        bottomAppBar = findViewById(R.id.bottomAppBar);
        faButton = findViewById(R.id.fActionbtn);
        recStartDate = findViewById(R.id.startdate_search);
        recEndDate = findViewById(R.id.enddate_search);
        btnSearch = findViewById(R.id.btn_searchrec);
        recStartDate.setInputType(0);
        recEndDate.setInputType(0);//disable cursur
        setToday();

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
        googleSignInClient = GoogleSignIn.getClient(Search.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        DatePickerDialog.OnDateSetListener datedialog1 = (datePicker, year, month, day) -> {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, month);
            myCalendar1.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };


        DatePickerDialog.OnDateSetListener datedialog2 = (datePicker, year, month, day) -> {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, month);
            myCalendar2.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        recStartDate.setOnClickListener(view -> new DatePickerDialog(Search.this, datedialog1, myCalendar1
                .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                myCalendar1.get(Calendar.DAY_OF_MONTH)).show());
        recEndDate.setOnClickListener(view -> new DatePickerDialog(Search.this, datedialog2, myCalendar2
                .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                myCalendar2.get(Calendar.DAY_OF_MONTH)).show());

        //create two selections
        //validate from date and to date

        btnSearch.setOnClickListener(view -> {
            String StartDate = recStartDate.getText().toString();
            String EndDate = recEndDate.getText().toString();

            searchRec(StartDate, EndDate);

        });


        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext()
                        , AddRecord.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(0, 0);
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
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private void updateLabel() {
        String datePattern = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.getDefault());
        recStartDate.setText(sdf.format(myCalendar1.getTime()));
        recEndDate.setText(sdf.format(myCalendar2.getTime()));
    }

    private void setToday() {

        recStartDate.setText(TimeData.getToday());
        recEndDate.setText(TimeData.getToday());
    }

    private void searchRec(String startdate, String endDate) {
        if (CheckAllFields(startdate, endDate)) {
            Bundle bundle= new Bundle();
            bundle.putString("start",startdate);
            bundle.putString("end",endDate);

            Intent intent= new Intent(this, ViewRecord.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    private boolean CheckAllFields(String StartDate, String EndDate) {
        boolean timedata = true;
        if (recStartDate.length() == 0) {
            recStartDate.setError(getString(R.string.error_msg_mandatory));
            return false;
        }
        if (recEndDate.length() == 0) {
            recEndDate.setError(getString(R.string.error_msg_mandatory));
            return false;
        }
        try {
            timedata = TimeData.CompareTwoDates(StartDate, EndDate);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("search", e.getMessage());
        }

        if (!timedata) {
            recStartDate.setError(getString(R.string.error_msg_invalid_date));
            return false;
        }
        return true;
    }

}
