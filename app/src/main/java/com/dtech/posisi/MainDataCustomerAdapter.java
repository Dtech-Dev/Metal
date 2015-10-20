package com.dtech.posisi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtech.orm2.MtlPelanggan;

import java.util.List;

/**
 * Created by Administrator on 28/08/2015.
 */
public class MainDataCustomerAdapter extends RecyclerView.Adapter<MainDataCustomerAdapter.MainViewHolder>{


    private List<MtlPelanggan> data;
    private Context context;
    String handleLat;
    String handleLong;


    public MainDataCustomerAdapter(Context context,List<MtlPelanggan> customers) {
        this.context = context;
        this.data = customers;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_main_row, null);
        MainViewHolder viewHolder = new MainViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        MtlPelanggan dataCustomer = data.get(position);

        holder.title.setText(Html.fromHtml(dataCustomer.getName()));
        holder.address.setText((Html.fromHtml(dataCustomer.getAddress())));
    }

    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }


    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView lastVisit;
        TextView title;
        TextView address;
        TextView lLat;
        TextView lLong;
        ImageView showUpImageHolder;
        RelativeLayout rel;

        public MainViewHolder(View itemView) {
            super(itemView);
            //rel = (RelativeLayout) itemView.findViewById(R.id.rel);

            context = itemView.getContext();
            title = (TextView)itemView.findViewById(R.id.mlistText);
            address = (TextView) itemView.findViewById(R.id.listText2);
           /* lastVisit = (TextView) itemView.findViewById(R.id.mlistlastvisit);
            lLat = (TextView) itemView.findViewById(R.id.lLat);
            lLong = (TextView) itemView.findViewById(R.id.lLong);
            showUpImageHolder = (ImageView) itemView.findViewById(R.id.showUp);*/
        }


    }


}
