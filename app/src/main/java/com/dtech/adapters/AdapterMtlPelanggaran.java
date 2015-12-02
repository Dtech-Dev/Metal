package com.dtech.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.orm.MtlImagePelanggaran;
import com.dtech.orm.MtlPelanggaran;
import com.dtech.posisi.R;

import java.util.List;

/**
 * Created by ADIST on 12/2/2015.
 */
public class AdapterMtlPelanggaran
        extends RecyclerView.Adapter<AdapterMtlPelanggaran.FoulViewHolder> {

    private List<MtlPelanggaran> mtlPelanggarans;
    private Context context;

    public AdapterMtlPelanggaran(Context context
            , List<MtlPelanggaran> mtlPelanggarans){
        this.context = context;
        this.mtlPelanggarans = mtlPelanggarans;
    }

    @Override
    public FoulViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recycle_foul_row, null);
        FoulViewHolder viewHolder = new FoulViewHolder(rootview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoulViewHolder holder, int position) {
        MtlPelanggaran fouls = mtlPelanggarans.get(position);
        holder.id.setText(fouls.getId().toString());
        holder.customer.setText(fouls.getPelanggan().getName());
        holder.foulDate.setText(fouls.getFoulDate());
        holder.foulType.setText(fouls.getFoulType());
        holder.tariff.setText(fouls.getTariff());
        holder.daya.setText(fouls.getDaya().toString());
        long totalImage =
                MtlImagePelanggaran.count(MtlImagePelanggaran.class, "foul_id = ?"
                        , new String[] {fouls.getId().toString()});
        holder.totalImages.setText("Total Image(s) : " + String.valueOf(totalImage));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Available Soon!", Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != mtlPelanggarans ? mtlPelanggarans.size() : 0);
    }

    public class FoulViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayout;
        private TextView id;
        private TextView customer;
        private TextView foulDate;
        private TextView foulType;
        private TextView tariff;
        private TextView daya;
        private TextView totalImages;

        public FoulViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relLayoutFouls);
            id = (TextView) itemView.findViewById(R.id.tvFoulID);
            customer = (TextView) itemView.findViewById(R.id.tvFoulID);
            foulDate = (TextView) itemView.findViewById(R.id.tvFoulDate);
            foulType = (TextView) itemView.findViewById(R.id.tvFoulType);
            tariff = (TextView) itemView.findViewById(R.id.tvTariff);
            daya = (TextView) itemView.findViewById(R.id.tvDaya);
            totalImages = (TextView) itemView.findViewById(R.id.tvCountImage);
        }
    }
}
