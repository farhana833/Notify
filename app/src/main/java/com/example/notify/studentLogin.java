package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class studentLogin extends AppCompatActivity {

    EditText rollno;
    EditText password;
    Button loginBtn;
    TextView signUpTxt;
    DatabaseReference dbRef;
    String studentYear;

    Dialog dialogFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        rollno = findViewById(R.id.rollno_login);
        password = findViewById(R.id.password_login);
        loginBtn = findViewById(R.id.student_login);
        signUpTxt = findViewById(R.id.signup_txt);

        dialogFailure = new Dialog(this);
        dialogFailure.setContentView(R.layout.custom_box_failure);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogFailure.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_background));
        }
        dialogFailure.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogFailure.setCancelable(false); //Optional
        dialogFailure.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button OkayFailure = dialogFailure.findViewById(R.id.btn_okay);

        OkayFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFailure.dismiss();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckAllFields()){
                    dbRef = FirebaseDatabase.getInstance().getReference();
                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean authSuccess = false;
                            for(DataSnapshot student : snapshot.child("students").getChildren()){
                                String stuRoll = rollno.getText().toString();
                                String stuPass = password.getText().toString();
                                String dbRoll = student.child("rollno").getValue().toString();
                                studentYear = student.child("rollno").getValue().toString();
                                String dbPass = student.child("password").getValue().toString();
                                if(stuRoll.equals(dbRoll) && stuPass.equals(dbPass))
                                {
                                    authSuccess = true;
                                }
                            }
                            if(authSuccess)
                            {
                                OpenStudentDashboard();
                            }
                            else{
                                dialogFailure.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            dialogFailure.show();
                        }
                    });
                }
            }
        });

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenStudentSignIn();
            }
        });
    }

    public boolean CheckAllFields() {
        if (rollno.length() == 0) {
            rollno.setError("This field is required");
            return false;
        }
        if (rollno.length() != 10) {
            rollno.setError("Must only have 10 characters");
            return false;
        }
        if (!isIntegerString(rollno.getText().toString())) {
            rollno.setError("Must have only numeric characters");
            return false;
        }
        if (!rollNoValidation(rollno.getText().toString())) {
            rollno.setError("Enter a valid roll number");
            return false;
        }
        if (password.length() == 0) {
            password.setError("This field is required");
            return false;
        }
        return true;
    }

    public void OpenStudentDashboard(){
        Intent intent = new Intent(this, studentDashboard.class);
        //Toast.makeText(studentLogin.this,"Rollno" + studentYear, Toast.LENGTH_LONG).show();
        intent.putExtra("studentYear", studentYear);
        startActivity(intent);
    }

    public void OpenStudentSignIn() {
        Intent intent = new Intent(this, studentSignIn.class);
        startActivity(intent);
    }

    public boolean isIntegerString(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    public boolean rollNoValidation(String input) {
        Calendar cal = Calendar.getInstance();
        int studentRollYear = Integer.parseInt(input.substring(0,4));
        int midValue = Integer.parseInt(input.substring(4,8));
        int endValue = Integer.parseInt(input.substring(8,10));
        if ((studentRollYear >= 2018 && studentRollYear <= cal.get(Calendar.YEAR)) &&
                (midValue == 2390 || midValue == 2420) &&
                (endValue <= 30 && endValue >= 1)) {
            return true;
        }
        else {
            return false;
        }
    }
}