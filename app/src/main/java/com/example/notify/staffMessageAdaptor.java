package com.example.notify;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class staffMessageAdaptor extends FirebaseRecyclerAdapter<messagesStaff, staffMessageAdaptor.staffViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public staffMessageAdaptor(@NonNull FirebaseRecyclerOptions<messagesStaff> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull staffViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull messagesStaff model) {
        holder.title.setText(model.getTitle());
        holder.sendBy.setText(model.getSendBy());
        holder.sendTo.setText(model.getSendTo());
        holder.descp.setText(model.getDescp());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus updateDialog = DialogPlus.newDialog(holder.title.getContext())
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.activity_sendmsg_update))
                        .setMargin(10, 10, 10, 10 )
                        .setExpanded(false)
                        .create();

                View updateView = updateDialog.getHolderView();

                EditText title = updateView.findViewById(R.id.title_update);
                EditText descp = updateView.findViewById(R.id.msgdesc_update);

                Button updateBtn = updateView.findViewById(R.id.updateButton);

                title.setText(model.getTitle());
                descp.setText(model.getDescp());

                updateDialog.show();

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", title.getText().toString());
                        map.put("descp", descp.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("messages")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                                        builder.setTitle("Success");
                                        builder.setMessage("Message Updated Successfully !");

                                        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                updateDialog.dismiss();
                                            }
                                        });

                                        builder.show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                                        builder.setTitle("Failure");
                                        builder.setMessage("Error while updating message !");

                                        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                updateDialog.dismiss();
                                            }
                                        });

                                        builder.show();
                                    }
                                });
                    }
                });
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                        builder.setTitle("Are you Sure ?");
                        builder.setMessage("Cannot Undo Delete");

                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("messages")
                                        .child(getRef(position).getKey())
                                        .removeValue();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        builder.show();
                    }
                });
    }

    @NonNull
    @Override
    public staffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_staff, parent, false);
        return new staffViewHolder(view);
    }

    class staffViewHolder extends RecyclerView.ViewHolder{

        TextView title, sendBy, sendTo, descp;
        ImageView editBtn, deleteBtn;

        public staffViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            sendBy = (TextView) itemView.findViewById(R.id.sendBy);
            sendTo = (TextView) itemView.findViewById(R.id.sendTo);
            descp = (TextView) itemView.findViewById(R.id.description);

            editBtn = (ImageView) itemView.findViewById(R.id.editicon);
            deleteBtn = (ImageView) itemView.findViewById(R.id.deleteicon);

        }
    }
}