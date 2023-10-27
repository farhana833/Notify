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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class studentSignIn extends AppCompatActivity {

    EditText name;
    EditText rollNo;
    EditText phoneNo;
    EditText password;
    EditText cpassword;
    Button signUpBtn;
    private Dialog dialogSuccess, dialogFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_in);

        name = findViewById(R.id.name_signup);
        rollNo = findViewById(R.id.rollno_signup);
        phoneNo = findViewById(R.id.mobile_signup);
        password = findViewById(R.id.password_signup);
        cpassword = findViewById(R.id.confirmPassword_signup);
        signUpBtn = findViewById(R.id.btnSignUp);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckAllFields() == true) {
                    insertStudentData();
                }
            }
        });


        //Create the Success Dialog here
        dialogSuccess = new Dialog(this);
        dialogSuccess.setContentView(R.layout.custom_box_msgsuccess);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogSuccess.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_background));
        }
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.setCancelable(false); //Optional
        dialogSuccess.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button OkaySuccess = dialogSuccess.findViewById(R.id.btn_okay);

        OkaySuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccess.dismiss();
                Intent intent = new Intent(studentSignIn.this, studentLogin.class);
                startActivity(intent);
            }
        });

        //Create the Success Dialog here
        dialogFailure = new Dialog(this);
        dialogFailure.setContentView(R.layout.custom_box_warning);
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
                Intent intent = new Intent(studentSignIn.this, studentSignIn.class);
                startActivity(intent);
            }
        });
    }

    public boolean CheckAllFields() {
        if (name.length() == 0) {
            name.setError("This field is required");
            return false;
        }
        if (rollNo.length() == 0) {
            rollNo.setError("This field is required");
            return false;
        }
        if (rollNo.length() != 10) {
            rollNo.setError("Must only have 10 characters");
            return false;
        }
        if (!isIntegerString(rollNo.getText().toString())) {
            rollNo.setError("Must have only numeric characters");
            return false;
        }
        if (!rollNoValidation(rollNo.getText().toString())) {
            rollNo.setError("Enter a valid roll number");
            return false;
        }
        if (phoneNo.length() == 0) {
            phoneNo.setError("This field is required");
            return false;
        }
        if (phoneNo.length() != 10) {
            phoneNo.setError("Must only have 10 characters");
            return false;
        }
        if (password.length() == 0) {
            password.setError("This field is required");
            return false;
        }
        if (password.length() < 8) {
            password.setError("Password must be minimum 8 Characters");
            return false;
        }
        else if (!(password.getText().toString()).equals(cpassword.getText().toString())) {
            cpassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    public void StudentLogin() {
        Intent intent = new Intent(this, studentLogin.class);
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
        if ((studentRollYear >= 2019 && studentRollYear <= cal.get(Calendar.YEAR)) &&
                (midValue == 2390 || midValue == 2420) &&
                (endValue <= 30 && endValue >= 1)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void insertStudentData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("rollno", rollNo.getText().toString());
        map.put("phoneno", phoneNo.getText().toString());
        map.put("password", password.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("students").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialogSuccess.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogFailure.show();
                    }
                });
    }
}