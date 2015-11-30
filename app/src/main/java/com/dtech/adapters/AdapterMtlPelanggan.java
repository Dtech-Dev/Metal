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
//        holder.lastVisit.setText(dataCustomer.getLastVisit());
        holder.showUpImageHolder.setImageBitmap(
                DefaultOps.decodeImage(dataCustomer.getLastImage()));

        txt = holder.title.getText().toString();
        indexString = txt.charAt(0);
        int selectedAlphabet;
        switch (indexString) {
            case 'a' | 'A': selectedAlphabet = R.drawable.a; break;
            case 'b' | 'B': selectedAlphabet = R.drawable.b; break;
            case 'c' | 'C': selectedAlphabet = R.drawable.c; break;
            case 'd' | 'D': selectedAlphabet = R.drawable.d; break;
            case 'e' | 'E': selectedAlphabet = R.drawable.e; break;
            case 'f' | 'F': selectedAlphabet = R.drawable.f; break;
            case 'g' | 'G': selectedAlphabet = R.drawable.g; break;
            case 'h' | 'H': selectedAlphabet = R.drawable.h; break;
            case 'i' | 'I': selectedAlphabet = R.drawable.i; break;
            case 'j' | 'J': selectedAlphabet = R.drawable.j; break;
            case 'k' | 'K': selectedAlphabet = R.drawable.k; break;
            case 'l' | 'L': selectedAlphabet = R.drawable.l; break;
            case 'm' | 'M': selectedAlphabet = R.drawable.m; break;
            case 'n' | 'N': selectedAlphabet = R.drawable.n; break;
            case 'o' | 'O': selectedAlphabet = R.drawable.o; break;
            case 'p' | 'P': selectedAlphabet = R.drawable.p; break;
            case 'q' | 'Q': selectedAlphabet = R.drawable.q; break;
            case 'r' | 'R': selectedAlphabet = R.drawable.r; break;
            case 's' | 'S': selectedAlphabet = R.drawable.s; break;
            case 't' | 'T': selectedAlphabet = R.drawable.t; break;
            case 'u' | 'U': selectedAlphabet = R.drawable.u; break;
            case 'v' | 'V': selectedAlphabet = R.drawable.v; break;
            case 'w' | 'w': selectedAlphabet = R.drawable.w; break;
            case 'x' | 'X': selectedAlphabet = R.drawable.x; break;
            case 'y' | 'Y': selectedAlphabet = R.drawable.y; break;
            case 'z' | 'Z': selectedAlphabet = R.drawable.z; break;
            default: selectedAlphabet = R.drawable.no_image_large; break;
        }

        holder.showUpImageHolder.setImageResource(selectedAlphabet);



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
