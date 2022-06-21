package com.app.pinpotha_beta.ui.records;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import com.app.pinpotha_beta.R;
import com.app.pinpotha_beta.ui.bottom_bar.ProfileActivity;
import com.app.pinpotha_beta.ui.bottom_bar.Translate;
import com.app.pinpotha_beta.ui.bottom_bar.SideMenu;
import com.app.pinpotha_beta.util.TimeData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.UUID;

public class AddRecord extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    BottomAppBar bottomAppBar;
    TextInputEditText deldate,description;
    final Calendar myCalendar = Calendar.getInstance();
    Button btn_add;
    FirebaseFirestore db;
    Spinner mainCat,subCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        // Assign variable
        bottomAppBar=findViewById(R.id.bottomAppBar);
        deldate = findViewById(R.id.deldate);
        deldate.setInputType(0);
        description= findViewById(R.id.descriptiondata);
        btn_add=findViewById(R.id.btn_add);
        setToday();
        mainCat=(Spinner) findViewById(R.id.mainspinner);
        subCat=(Spinner) findViewById(R.id.subspinner);

        ArrayAdapter<CharSequence> mainType
                = ArrayAdapter.createFromResource(
                this, R.array.main_type,
                android.R.layout.simple_spinner_item);
        mainType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainCat.setAdapter(mainType);

        //set sub types
        ArrayAdapter<CharSequence> subType1
                = ArrayAdapter.createFromResource(
                this, R.array.sub_type1,
                android.R.layout.simple_spinner_item);
        subType1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> subType2
                = ArrayAdapter.createFromResource(
                this, R.array.sub_type2,
                android.R.layout.simple_spinner_item);
        subType2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        //end set sub types
        ArrayAdapter<CharSequence> not_available
                = ArrayAdapter.createFromResource(
                this, R.array.not_avilable,
                android.R.layout.simple_spinner_item);
        not_available.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //child spinner logic
        mainCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    subCat.setAdapter(subType1);
                }
                else if(position==1){
                    subCat.setAdapter(subType2);
                }
                else{
                    subCat.setAdapter(not_available);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //end child spinner

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
        Log.d("uid:",firebaseUser.getUid().trim());
        // Initialize sign in client
        googleSignInClient= GoogleSignIn.getClient(AddRecord.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        DatePickerDialog.OnDateSetListener datedialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
          myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };


        deldate.setOnClickListener(new View.OnClickListener() {
            @NonNull
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddRecord.this, datedialog, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                Toast.makeText(AddRecord.this,"btn clicked",Toast.LENGTH_SHORT);
            }

        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recDate=deldate.getText().toString();
                String recMain=(String) mainCat.getSelectedItem();
                String recSub=(String) subCat.getSelectedItem();
                String recdisc=description.getText().toString();
                String fbUser=firebaseUser.getUid();
                saveToFirestore(recDate,recdisc,recMain,recSub,fbUser);

            }
        });

        //to get value of date
        // deldate.getText().toString(),
        //deldate.setError(getString(R.string.error_msg_mandatory));

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
                            , ViewRecord.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void saveToFirestore(String recDate, String recdisc,String recMain,String recSub,String fbuser) {
        //validate fields are empty
        //getCounterMaxLength

        HashMap<String,Object> map= new HashMap<>();
        map.put("main",recMain);
        map.put("sub",recSub);
        map.put("date",recDate);
        map.put("desc",recdisc);
        Log.d("path:","user/"+fbuser+"/pina/");
        db.collection("user/"+fbuser+"/pina/").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(AddRecord.this,"Record added",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(AddRecord.this,"Record add fail",Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void updateLabel() {
        String datePattern = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.getDefault());

        deldate.setText(sdf.format(myCalendar.getTime()));
    }

    private void setToday() {
        deldate.setText(TimeData.getToday());
    }

}