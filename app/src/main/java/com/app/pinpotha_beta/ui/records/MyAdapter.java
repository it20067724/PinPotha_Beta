package com.app.pinpotha_beta.ui.records;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pinpotha_beta.MainActivity;
import com.app.pinpotha_beta.R;
import com.app.pinpotha_beta.util.TimeData;
import com.google.type.DateTime;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ViewRecord activity;
    private List<Model> mList;

    public MyAdapter(ViewRecord activity,List<Model> mList){
        this.activity=activity;
        this.mList=mList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(activity).inflate(R.layout.item,parent,false);
      return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(mList.get(position).getTitle());
    //    holder.id.setText(mList.get(position).getId());
        holder.subtitle.setText(mList.get(position).getSubtitle());
        holder.decription.setText(mList.get(position).getDecription());
        holder.date.setText(TimeData.conUiDate(mList.get(position).getDate()));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();
                bundle.putString("id",mList.get(position).getId());
                bundle.putString("title",mList.get(position).getTitle());
                bundle.putString("subtitle",mList.get(position).getSubtitle());
                bundle.putString("desc",mList.get(position).getDecription());
                bundle.putString("date", TimeData.conUiDate(mList.get(position).getDate()));
                Intent intent= new Intent(activity, EditRecord.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id,title,subtitle,decription,date;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
         //   id= itemView.findViewById(R.id.cardduid);
            title= itemView.findViewById(R.id.cardTitle);
            subtitle= itemView.findViewById(R.id.cardsubtitle);
            decription= itemView.findViewById(R.id.carddescription);
            date= itemView.findViewById(R.id.cardddate);
            card=itemView.findViewById(R.id.card);
        }

    }
}
