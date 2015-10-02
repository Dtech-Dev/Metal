package com.dtech.posisi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dtech.orm.ImageCustomer;

import java.util.List;

/**
 * Created by aris on 30/09/15.
 */
public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private List<ImageCustomer> imgCustomer;
    private static LayoutInflater inflater = null;

    public GalleryAdapter(Context context, List<ImageCustomer> imgCustomer){
        this.setImgCustomer(imgCustomer);
        this.setContext(context);
        setInflater((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        GalleryAdapter.inflater = inflater;
    }

    @Override
    public int getCount() {
        return getImgCustomer().size();
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
        View view = convertview;
        if (convertview == null) {
            view = getInflater().inflate(R.layout.gallery_row, null);
        }

        ImageView item = (ImageView)view.findViewById(R.id.imageRow);
        ImageCustomer imgCust = getImgCustomer().get(i);
        String lastImage = imgCust.getImageTest();
        if (lastImage != null) {
            item.setImageBitmap(ImageCustomer.decodeImage(lastImage));
        } else {
            item.setBackgroundResource(R.drawable.no_image_large);
        }

        //item.setImageBitmap();

        return view;
    }

    public List<ImageCustomer> getImgCustomer() {
        return imgCustomer;
    }

    public void setImgCustomer(List<ImageCustomer> imgCustomer) {
        this.imgCustomer = imgCustomer;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
