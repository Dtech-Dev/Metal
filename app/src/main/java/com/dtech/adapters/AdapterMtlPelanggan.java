package com.dtech.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtech.orm.DefaultOps;
import com.dtech.orm.MtlPelanggan;
import com.dtech.posisi.DetailActivity;
import com.dtech.posisi.R;

import java.util.List;

/**
 * Created by Administrator on 28/08/2015.
 */
public class AdapterMtlPelanggan extends RecyclerView.Adapter<AdapterMtlPelanggan.MainViewHolder>{

    private List<MtlPelanggan> mtlPelanggans;
    private Context context;

    public AdapterMtlPelanggan(Context context, List<MtlPelanggan> mtlPelanggans) {
        this.context = context;
        this.mtlPelanggans = mtlPelanggans;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.custom_main_row, null);
        MainViewHolder viewHolder = new MainViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        MtlPelanggan dataCustomer = mtlPelanggans.get(position);
        holder.code.setText(dataCustomer.getCode());
        holder.title.setText(dataCustomer.getName());
        holder.address.setText(dataCustomer.getAddress());
        holder.lastVisit.setText(dataCustomer.getLastVisit());
        holder.showUpImageHolder.setImageBitmap(
                DefaultOps.decodeImage(dataCustomer.getLastImage()));
        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cekI = new Intent(context, DetailActivity.class);
                context.startActivity(cekI);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mtlPelanggans ? mtlPelanggans.size() : 0);
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rel;
        TextView lastVisit;
        TextView code;
        TextView title;
        TextView address;
        ImageView showUpImageHolder;

        public MainViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            rel = (RelativeLayout) itemView.findViewById(R.id.relLayout);
            code = (TextView) itemView.findViewById(R.id.mlistCode);
            title = (TextView)itemView.findViewById(R.id.mlistName);
            address = (TextView) itemView.findViewById(R.id.mlistaddress);
            lastVisit = (TextView) itemView.findViewById(R.id.mlistlastvisit);
            showUpImageHolder = (ImageView) itemView.findViewById(R.id.showUp);
        }
    }
}
