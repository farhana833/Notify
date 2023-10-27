package com.example.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewMsg extends AppCompatActivity {

    RecyclerView recyclerView;
    staffMessageAdaptor myStfAdpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_msg);
        String staffNameView = getIntent().getStringExtra("staffName");

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseDatabase.getInstance().getReference().child("messages").orderByChild("sendBy").equalTo(staffNameView);
        FirebaseRecyclerOptions<messagesStaff> options =
                new FirebaseRecyclerOptions.Builder<messagesStaff>()
                        .setQuery(query, messagesStaff.class)
                        .build();

        myStfAdpt = new staffMessageAdaptor(options);
        recyclerView.setAdapter(myStfAdpt);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myStfAdpt.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myStfAdpt.stopListening();
    }
}