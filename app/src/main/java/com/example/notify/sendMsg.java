package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class sendMsg extends AppCompatActivity {

    EditText title;
    EditText msgDesc;
    Button sendBtn;
    Dialog dialogSuccess, dialogFailure;

    String[] category = { "1st Year", "2nd Year", "3rd Year", "4th Year", "5th Year", "All" };
    AutoCompleteTextView autoTxtView;
    ArrayAdapter<String> adapterItems;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        String sendBy = getIntent().getStringExtra("staffName");

        title = findViewById(R.id.title_send);
        msgDesc = findViewById(R.id.msgdesc_send);
        autoTxtView = findViewById(R.id.select_send);
        sendBtn = findViewById(R.id.send);

        adapterItems = new ArrayAdapter<String> (this, R.layout.dropdown, category);
        autoTxtView.setAdapter(adapterItems);

        autoTxtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + autoTxtView.getText().toString(), Toast.LENGTH_SHORT);
            }
        });

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
                Intent intent = new Intent(sendMsg.this, viewMsg.class);
                intent.putExtra("staffName", sendBy);
                startActivity(intent);
            }
        });

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
//                Intent intent = new Intent(sendMsg.this, staffDashboard.class);
//                intent.putExtra("staffName", sendBy);
//                startActivity(intent);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckAllFields())
                {
                    sendNotificationFunction(sendBy);
                    insertMsg(sendBy);
                }
            }
        });
    }

    public boolean CheckAllFields() {
        if (title.length() == 0) {
            title.setError("This field is required");
            return false;
        }
        if (title.length() > 25) {
            title.setError("Maximum upto 25 characters only");
        }
        if (autoTxtView.length() == 0) {
            autoTxtView.setError("This field is required");
            return false;
        }
        if (msgDesc.length() == 0) {
            msgDesc.setError("This field is required");
            return false;
        }
        return true;
    }

    private void insertMsg(String sendby) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title.getText().toString());
        map.put("sendBy", sendby);
        map.put("sendTo", autoTxtView.getText().toString());
        map.put("descp", msgDesc.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("messages").push()
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

    public int toInt(String stuRoll) {
        int intNum = Integer.parseInt(stuRoll.substring(0,4));
        return intNum;
    }

    public void sendNotificationFunction(String sendBy) {
        int StudentYear = findStudentYear(autoTxtView.getText().toString());
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //int count  = 0;
                //Toast.makeText(sendMsg.this,"given by staff" + StudentYear, Toast.LENGTH_LONG).show();
                for(DataSnapshot student : snapshot.child("students").getChildren()){
                    String dbStuRollNum = student.child("rollno").getValue().toString();
                    int checkVal = toInt(dbStuRollNum);
                    if(checkVal == StudentYear || autoTxtView.getText().toString().equals("All"))
                    {
                        //count = count+1;
                        //Toast.makeText(sendMsg.this,"from database" + checkVal, Toast.LENGTH_LONG).show();
                        sendNotification sendStudentNotification = new sendNotification(
                                "/topics/all", title.getText().toString(), sendBy,
                                getApplicationContext(), sendMsg.this
                        );
                        sendStudentNotification.sendNotify();
                    }
                }
                //Toast.makeText(sendMsg.this,"Count" + count, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogFailure.show();
            }
        });
    }
}
