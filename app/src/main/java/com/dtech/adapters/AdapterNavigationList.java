package com.dtech.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtech.posisi.FrgmNavDrawer;
import com.dtech.posisi.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 24/08/2015.
 */
public class AdapterNavigationList extends RecyclerView.Adapter<AdapterNavigationList.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    List<MenuHolder> data = Collections.emptyList();

    public AdapterNavigationList(Context context, List<MenuHolder> data){
        this.context=context;
        this.data=data;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenuHolder current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        FrgmNavDrawer mDrawer;
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

    public static class MenuHolder {
        String title;
        int iconId;

        public MenuHolder(String title, int iconId) {
            this.title = title;
            this.iconId = iconId;
        }
    }
}
