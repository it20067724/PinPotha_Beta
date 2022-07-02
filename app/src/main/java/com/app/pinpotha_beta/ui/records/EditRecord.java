package com.app.pinpotha_beta.ui.records;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.app.pinpotha_beta.R;
import com.app.pinpotha_beta.ui.bottom_bar.ProfileActivity;
import com.app.pinpotha_beta.ui.bottom_bar.Search;
import com.app.pinpotha_beta.ui.bottom_bar.SideMenu;
import com.app.pinpotha_beta.ui.bottom_bar.Translate;
import com.app.pinpotha_beta.ui.ketayam.NetworkChangeListener;
import com.app.pinpotha_beta.util.TimeData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class EditRecord extends AppCompatActivity {


    String id,title,subtitle,decription,date;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    BottomAppBar bottomAppBar;
    TextInputEditText deldate, description,mainCat,subCat;
    final Calendar myCalendar = Calendar.getInstance();
    Button btn_update,btn_delete;
    FirebaseFirestore db;
    NetworkChangeListener networkChangeListener=new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        deldate = findViewById(R.id.lbl_edit_date);
        deldate.setInputType(0);
        description = findViewById(R.id.edit_descriptiondata);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        mainCat= findViewById(R.id.edit_main_cat);
        mainCat.setEnabled(false);
        subCat= findViewById(R.id.edit_subcat);
        subCat.setEnabled(false);

        db = FirebaseFirestore.getInstance();

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
        Log.d("uid:", firebaseUser.getUid().trim());
        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(EditRecord.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        //after db is initialized
        Bundle bundle = getIntent().getExtras();
        ;
        if (bundle != null) {
            id = bundle.getString("id");
            title= bundle.getString("title");
            subtitle= bundle.getString("subtitle");
            decription= bundle.getString("desc");
            date= bundle.getString("date");
            deldate.setText(date);
            description.setText(decription);
            mainCat.setText(title);
                    subCat.setText(subtitle);
        }

        DatePickerDialog.OnDateSetListener datedialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recDate=deldate.getText().toString();
                String recdisc=description.getText().toString();

                String fbUser=firebaseUser.getUid();
                updateToFirestore(recDate,recdisc,fbUser,id);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fbUser=firebaseUser.getUid();
                deleteFromFirestore(fbUser,id);
            }
        });

        deldate.setOnClickListener(new View.OnClickListener() {
            @NonNull
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditRecord.this, datedialog, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                Toast.makeText(EditRecord.this, "btn clicked", Toast.LENGTH_SHORT);
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
                            , ViewRecord.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
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

    private void deleteFromFirestore(String fbuser, String id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.msg_are_u_sure));
        builder.setMessage((getString(R.string.msg_confirm_delete)
                +" "+
                (getString(R.string.msg_confirm_delete_canot_be_undone))));
        builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                db.collection("user/"+fbuser+"/pina/").document(id)
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EditRecord.this,"Record Deleted",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext()
                                        , Search.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                overridePendingTransition(0, 0);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditRecord.this,"Record Delete fail",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        builder.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();


    };

    private void updateToFirestore(String recDate, String recdisc, String fbuser, String id) {
      db.collection("user/"+fbuser+"/pina/").document(id)
           .update("date",TimeData.conDateDB(recDate),"desc",recdisc)
           .addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   Toast.makeText(EditRecord.this,"Record Updated",Toast.LENGTH_LONG).show();
                   Bundle bundle= new Bundle();
                   bundle.putString("start",recDate);
                   bundle.putString("end",recDate);
                   Intent intent= new Intent(EditRecord.this, ViewRecord.class);
                   intent.putExtras(bundle);
                   startActivity(intent);
               }
           })
           .addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(EditRecord.this,"Record Update fail",Toast.LENGTH_LONG).show();
               }
           });

    }


    private void updateLabel() {
        String datePattern = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.getDefault());

        deldate.setText(sdf.format(myCalendar.getTime()));
    }


}