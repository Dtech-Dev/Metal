package com.dtech.posisi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 28/08/2015.
 */
public class MainCustomerAdapter extends RecyclerView.Adapter<MainCustomerAdapter.MainViewHolder> {

    private Context context;
    String handleString;


    List<Information> data= Collections.emptyList();

    public MainCustomerAdapter(List<Information> data){

        this.data=data;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_row, parent,false);
        MainViewHolder holder=new MainViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(final MainViewHolder holder, int position) {
        Information current=data.get(position);
        holder.title.setText(current.nama);
        holder.address.setText(current.address);
        holder.lLat.setText(current.latTude);
        holder.lLong.setText(current.longTude);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleString = holder.title.getText().toString();
                //Toast.makeText(context, "Name Clicked "+handleString, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ShowLogActivity.class);
                intent.putExtra("stringHandled", handleString);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView address;
        TextView lLat;
        TextView lLong;


        public MainViewHolder(View itemView) {
            super(itemView);

            context=itemView.getContext();
            title=(TextView)itemView.findViewById(R.id.mlistnama);
            address = (TextView) itemView.findViewById(R.id.mlistaddress);
            lLat = (TextView) itemView.findViewById(R.id.lLat);
            lLong = (TextView) itemView.findViewById(R.id.lLong);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
