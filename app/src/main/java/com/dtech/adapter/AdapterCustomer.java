package com.dtech.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtech.orm.Customer;
import com.dtech.posisi.Information;
import com.dtech.posisi.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 21/09/2015.
 */
public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.MainViewHolder> {

    List<Information> data = Collections.emptyList();
    private Context context;

    public AdapterCustomer(List<Information> data) {
        this.data = data;
    }
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_main_row, parent, false);
        //MainViewHolder holder = new MainViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Information current = data.get(position);
       // holder.name.setText(current.);
    }

    @Override
    public int getItemCount() {
        return 0;
    }




    public class MainViewHolder extends RecyclerView.ViewHolder {
        //TextView name;
        public MainViewHolder(View itemView) {
            super(itemView);

          //  context = itemView.getContext();
            //name = (TextView) itemView.findViewById(R.id.mlistText);
        }
    }
}
