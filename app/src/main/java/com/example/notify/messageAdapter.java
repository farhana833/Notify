package com.example.notify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.util.List;


public class messageAdapter extends RecyclerView.Adapter<messageAdapter.myViewHolder> {

    List<messages> msgList;
    Context context;

    public messageAdapter (List<messages> msgList, Context context) {
        this.msgList = msgList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        messages msgs = msgList.get(position);

        holder.title.setText(msgs.getTitle());
        holder.sendBy.setText(msgs.getSendBy());
        holder.descp.setText(msgs.getDescp());

        boolean isExpandable = msgList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView title, sendBy, descp;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            sendBy = (TextView) itemView.findViewById(R.id.sendBy);
            descp = (TextView) itemView.findViewById(R.id.description);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
            expandableLayout = (RelativeLayout) itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                messages msgsview = msgList.get(getAbsoluteAdapterPosition());
                msgsview.setExpandable(!msgsview.isExpandable());
                notifyItemChanged(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}