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
import java.util.zip.Inflater;

/**
 * Created by aris on 30/09/15.
 */
public class GalleryAdapter extends BaseAdapter {

    Context context;
    List<Customer> setimages;
    private static LayoutInflater inflater = null;

    public GalleryAdapter(Context context, List<Customer> setimages) {
        this.context = context;
        this.setimages = setimages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return setimages.size();
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
        Customer customer = setimages.get(i);
        View view = convertview;
        if (convertview == null) {
            view = inflater.inflate(R.layout.gallery_row, null);
        }

        ImageView item = (ImageView)view.findViewById(R.id.imageRow);
        String lastImage = customer.lastImage();
        if (lastImage != null) {
            item.setImageBitmap(ImageCustomer.decodeImage(lastImage));
        }

        //item.setImageBitmap();

        return view;
    }
}
