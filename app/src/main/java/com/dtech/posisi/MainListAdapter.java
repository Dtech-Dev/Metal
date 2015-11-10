package com.dtech.posisi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
@Deprecated
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainViewHolder> {

    private Context context;


    List<Information> data= Collections.emptyList();

    public MainListAdapter(List<Information> data){

        this.data=data;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_main_row, parent,false);
        MainViewHolder holder=new MainViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Information current=data.get(position);
        holder.title.setText(current.mainTitle);
        holder.icon.setImageResource(current.mainIconId);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{
        TextView title;
        ImageView icon;

        public MainViewHolder(View itemView) {
            super(itemView);

            context=itemView.getContext();
            title=(TextView)itemView.findViewById(R.id.mlistName);
            icon=(ImageView)itemView.findViewById(R.id.mlistIcon);
        }

        /*@Override
        public void onClick(View view) {
            try {
                String ambilString = title.getText().toString();
                Toast.makeText(context.getApplicationContext(), "Done "+ambilString,Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context.getApplicationContext(), "Fail",Toast.LENGTH_SHORT).show();
            }
        }*/

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //context.startActivity();
                /*try {
                    String ambilString = title.getText().toString();
                    //Toast.
                    Toast.makeText(context., "Done "+ambilString,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Fail",Toast.LENGTH_SHORT).show();
                }*/
            }
            return false;
        }
    }
}
