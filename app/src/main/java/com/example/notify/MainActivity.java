package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CardView staffBtn;
    CardView studentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        staffBtn = findViewById(R.id.staffBtn);
        studentBtn = findViewById(R.id.studentBtn);

        staffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaffLogin();
            }
        });

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentLogin();
            }
        });
    }

    public void StaffLogin() {
        Intent intent = new Intent(this, staffLogin.class);
        startActivity(intent);
    }

    public void StudentLogin() {
        Intent intent = new Intent(this, studentLogin.class);
        startActivity(intent);
    }
}