package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class studentDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    List<messages> msgsLst = new ArrayList<>();
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        String DbStuYear = getIntent().getStringExtra("studentYear");

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(studentDashboard.this));

        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msgsLst.clear();
                for(DataSnapshot messages : snapshot.child("messages").getChildren()){
                    String DbYear = messages.child("sendTo").getValue().toString();
                    int stuYear = findStudentYear(DbYear);
                    //Toast.makeText(studentDashboard.this,"The day of the week is " + DbStuYear, Toast.LENGTH_LONG).show();
                    //Toast.makeText(studentDashboard.this,"The day of the week is " + stuYear, Toast.LENGTH_LONG).show();
                    if(Integer.parseInt(DbStuYear.substring(0,4)) == stuYear || DbYear.equals("All"))
                    {
                        if (messages.hasChild("title")
                                && messages.hasChild("sendBy")
                                && messages.hasChild("sendTo")
                                && messages.hasChild("descp")
                        )
                        {
                            String title = messages.child("title").getValue(String.class);
                            String sendBy = messages.child("sendBy").getValue(String.class);
                            String sendTo = messages.child("sendTo").getValue(String.class);
                            String descp = messages.child("descp").getValue(String.class);

                            messages allmsgs = new messages(title, sendBy, sendTo, descp);
                            msgsLst.add(allmsgs);
                        }
                    }
                }
                recyclerView.setAdapter(new messageAdapter(msgsLst, studentDashboard.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public int findStudentYear(String selectValue) {
        int studentYear;
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        if(currentMonth >=7 && currentMonth <= 12)
        {
            switch (selectValue){
                case "1st Year":
                    studentYear = currentYear;
                    return studentYear;
                case "2nd Year":
                    studentYear = currentYear-1;
                    return studentYear;
                case "3rd Year":
                    studentYear = currentYear-2;
                    return studentYear;
                case "4th Year":
                    studentYear = currentYear-3;
                    return studentYear;
                case "5th Year":
                    studentYear = currentYear-4;
                    return studentYear;
                default:
                    studentYear = currentYear+1;
                    return studentYear;
            }
        }
        else {

            switch (selectValue){
                case "1st Year":
                    studentYear = currentYear-1;
                    return studentYear;
                case "2nd Year":
                    studentYear = currentYear-2;
                    return studentYear;
                case "3rd Year":
                    studentYear = currentYear-3;
                    return studentYear;
                case "4th Year":
                    studentYear = currentYear-4;
                    return studentYear;
                case "5th Year":
                    studentYear = currentYear-5;
                    return studentYear;
                default:
                    studentYear = currentYear;
                    return studentYear;
            }
        }
    }
}