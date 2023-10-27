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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class staffLogin extends AppCompatActivity {

    EditText staffId;
    Button signInButton;
    DatabaseReference dbRef;
    String dbStaffName;
    Dialog dialogFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        staffId = findViewById(R.id.staffId_login);
        signInButton = findViewById(R.id.signInButton);

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

        dbRef = FirebaseDatabase.getInstance().getReference().child("staff");

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckAllFields()){
                    if(checkAuth()) {
                        openStaffDashboard();
                    }
                }
            }
        });
    }

    public boolean CheckAllFields() {
        if (staffId.length() == 0) {
            staffId.setError("This field is required");
            return false;
        }
        return true;
    }

    public void openStaffDashboard(){
        Intent intent = new Intent(this, staffDashboard.class);
        intent.putExtra("staffName",dbStaffName);
        startActivity(intent);
    }

    public boolean checkAuth() {
        boolean authSuccess = false;
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot staff : snapshot.child("staff").getChildren()){
                    String stfId = staffId.getText().toString();
                    String dbStfId = staff.child("id").getValue().toString();
                    dbStaffName = staff.child("name").getValue().toString();
                    if(stfId.equals(dbStfId))
                        {
                            //authSuccess = true;
                            //Toast.makeText(staffLogin.this,"check auth " + authSuccess, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogFailure.show();
                }
            });
        return authSuccess;
    }
}