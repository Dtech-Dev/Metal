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
import com.dtech.orm.MtlImagePelanggaran;
import com.dtech.orm.MtlPelanggan;
import com.dtech.posisi.ActvtDetails;
import com.dtech.posisi.R;

import java.util.List;

/**
 * Created by Administrator on 28/08/2015.
 */
public class AdapterMtlPelanggan extends RecyclerView.Adapter<AdapterMtlPelanggan.MainViewHolder>{

    private List<MtlPelanggan> mtlPelanggans;
    private List<MtlImagePelanggaran> mtlImgPelanggans;
    private Context context;
    String name;

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
        final String txt;
        char indexString;
        MtlPelanggan dataCustomer = mtlPelanggans.get(position);
        holder.code.setText(dataCustomer.getCode());
        holder.title.setText(dataCustomer.getName());
        holder.address.setText(dataCustomer.getAddress());
        holder.lastVisit.setText(dataCustomer.getLastVisit());
        holder.showUpImageHolder.setImageBitmap(
                DefaultOps.decodeImage(dataCustomer.getLastImage()));

        txt = holder.title.getText().toString();
        indexString = txt.charAt(0);

        if ((indexString == 'a') || (indexString=='A')) {
            holder.showUpImageHolder.setImageResource(R.drawable.a);
        } else if ((indexString == 'b') || (indexString=='B')) {
            holder.showUpImageHolder.setImageResource(R.drawable.b);
        } else if ((indexString == 'c') || (indexString=='C')) {
            holder.showUpImageHolder.setImageResource(R.drawable.c);
        } else if ((indexString == 'd') || (indexString=='D')) {
            holder.showUpImageHolder.setImageResource(R.drawable.d);
        } else if ((indexString == 'e') || (indexString=='E')) {
            holder.showUpImageHolder.setImageResource(R.drawable.e);
        } else if ((indexString == 'f') || (indexString=='F')) {
            holder.showUpImageHolder.setImageResource(R.drawable.f);
        } else if ((indexString == 'g') || (indexString=='G')) {
            holder.showUpImageHolder.setImageResource(R.drawable.g);
        } else if ((indexString == 'h') || (indexString=='H')) {
            holder.showUpImageHolder.setImageResource(R.drawable.h);
        } else if ((indexString == 'i') || (indexString=='I')) {
            holder.showUpImageHolder.setImageResource(R.drawable.i);
        } else if ((indexString == 'j') || (indexString=='J')) {
            holder.showUpImageHolder.setImageResource(R.drawable.j);
        } else if ((indexString == 'k') || (indexString=='K')) {
            holder.showUpImageHolder.setImageResource(R.drawable.k);
        } else if ((indexString == 'l') || (indexString=='L')) {
            holder.showUpImageHolder.setImageResource(R.drawable.l);
        } else if ((indexString == 'm') || (indexString=='M')) {
            holder.showUpImageHolder.setImageResource(R.drawable.m);
        } else if ((indexString == 'n') || (indexString=='N')) {
            holder.showUpImageHolder.setImageResource(R.drawable.n);
        } else if ((indexString == 'o') || (indexString=='O')) {
            holder.showUpImageHolder.setImageResource(R.drawable.o);
        } else if ((indexString == 'p') || (indexString=='P')) {
            holder.showUpImageHolder.setImageResource(R.drawable.p);
        } else if ((indexString == 'q') || (indexString=='Q')) {
            holder.showUpImageHolder.setImageResource(R.drawable.q);
        } else if ((indexString == 'r') || (indexString=='R')) {
            holder.showUpImageHolder.setImageResource(R.drawable.r);
        } else if ((indexString == 's') || (indexString=='S')) {
            holder.showUpImageHolder.setImageResource(R.drawable.s);
        } else if ((indexString == 't') || (indexString=='T')) {
            holder.showUpImageHolder.setImageResource(R.drawable.t);
        } else if ((indexString == 'u') || (indexString=='U')) {
            holder.showUpImageHolder.setImageResource(R.drawable.u);
        } else if ((indexString == 'v') || (indexString=='V')) {
            holder.showUpImageHolder.setImageResource(R.drawable.v);
        } else if ((indexString == 'w') || (indexString=='W')) {
            holder.showUpImageHolder.setImageResource(R.drawable.w);
        } else if ((indexString == 'x') || (indexString=='X')) {
            holder.showUpImageHolder.setImageResource(R.drawable.x);
        } else if ((indexString == 'y') || (indexString=='Y')) {
            holder.showUpImageHolder.setImageResource(R.drawable.y);
        } else if ((indexString == 'z') || (indexString=='Z')) {
            holder.showUpImageHolder.setImageResource(R.drawable.z);
        } else  {
            holder.showUpImageHolder.setImageResource(R.drawable.a);
        }

        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Nama : "+txt,Toast.LENGTH_SHORT).show();
                Intent cekI = new Intent(context, ActvtDetails.class);
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
//            lastVisit = (TextView) itemView.findViewById(R.id.mlistlastvisit);
            showUpImageHolder = (ImageView) itemView.findViewById(R.id.mlistIcon);
        }
    }
}
