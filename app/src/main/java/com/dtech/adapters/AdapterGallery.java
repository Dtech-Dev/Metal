package com.dtech.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.dtech.orm.DefaultOps;
import com.dtech.orm.MtlImagePelanggaran;
import com.dtech.posisi.R;
import java.util.List;

/**
 * Created by aris on 30/09/15.
 */
public class AdapterGallery extends BaseAdapter {

    private Context context;
    private List<MtlImagePelanggaran> images;
    private static LayoutInflater inflater = null;

    public AdapterGallery(Context context, List<MtlImagePelanggaran> images){
        this.setImages(images);
        this.setContext(context);
        setInflater((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        AdapterGallery.inflater = inflater;
    }

    @Override
    public int getCount() {
        return getImages().size();
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
            view = getInflater().inflate(R.layout.layout_gallery_row, null);
        }

        ImageView item = (ImageView)view.findViewById(R.id.imageRow);
        MtlImagePelanggaran imgCust = getImages().get(i);
        String lastImage = imgCust.getImage();
        if (lastImage != null) {
            item.setImageBitmap(DefaultOps.decodeImage(lastImage));
        } else {
            item.setBackgroundResource(R.drawable.no_image_large);
        }
        return view;
    }

    public List<MtlImagePelanggaran> getImages() {
        return images;
    }

    public void setImages(List<MtlImagePelanggaran> images) {
        this.images = images;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
