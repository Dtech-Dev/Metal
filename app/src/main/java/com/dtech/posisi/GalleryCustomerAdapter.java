package com.dtech.posisi;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.dtech.orm.Customer;
import com.dtech.orm.ImageCustomer;

import java.util.List;

/**
 * Created by Administrator on 28/08/2015.
 */

public class GalleryCustomerAdapter extends BaseAdapter {

    Context context;
    List<Customer> setImage;

    private static LayoutInflater inflater = null;

    public GalleryCustomerAdapter(Context context, List<Customer> imgCust) {

        this.context = context;
        this.setImage = imgCust;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return setImage.size();
    }

    @Override
    public Object getItem(int i) {

        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        Customer customer = setImage.get(i);
        String lastImage = customer.lastImage();
        View view = convertview;
        if (convertview == null) {
            view = inflater.inflate(R.layout.gallery_row, null);
        }

        ImageView setImagecust = (ImageView) view.findViewById(R.id.imageList);

        setImagecust.setImageBitmap(ImageCustomer.decodeImage(lastImage));

        return view;
    }

}
