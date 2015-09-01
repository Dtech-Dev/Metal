package com.dtech.posisi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 24/08/2015.
 */
public class NavListAdapter extends RecyclerView.Adapter<NavListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    List<Information> data= Collections.emptyList();

    public NavListAdapter(Context context, List<Information> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current=data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NavDrawerFragment mDrawer;
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            context=itemView.getContext();
            title=(TextView)itemView.findViewById(R.id.listText);
            icon=(ImageView)itemView.findViewById(R.id.listIcon);


        }

        @Override
        public void onClick(View view) {

        }
    }
}
