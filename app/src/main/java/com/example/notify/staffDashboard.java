package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class staffDashboard extends AppCompatActivity {

    CardView sendMsg;

    CardView viewMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);
        String StaffNameDashboard = getIntent().getStringExtra("staffName");

        TextView tv = (TextView)findViewById(R.id.welcomeStaff);
        tv.setText("Hello!! "+ StaffNameDashboard);

        sendMsg = findViewById(R.id.sendmsg);
        viewMsg = findViewById(R.id.viewmsg);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(staffDashboard.this, sendMsg.class);
                intent.putExtra("staffName", StaffNameDashboard);
                startActivity(intent);
            }
        });

        viewMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(staffDashboard.this, viewMsg.class);
                intent.putExtra("staffName", StaffNameDashboard);
                startActivity(intent);
            }
        });
    }

}