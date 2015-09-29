package com.dtech.orm;

import android.media.Image;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by aris on 22/09/15.
 */

public class ImageCustomer extends SugarRecord<ImageCustomer> {

    Customer customer;
    String name;
    String longitude;
    String latitude;
    byte[] image;

    public ImageCustomer() {}

    public ImageCustomer(Customer customer, String name, String longitude, String latitude, byte[] image) {
        this.customer = customer;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.image = image;
    }

    public ImageCustomer(List<String> imagecustRecord) {
        this.name = imagecustRecord.get(1);
        this.longitude=imagecustRecord.get(2);
        this.latitude = imagecustRecord.get(3);

    }

    //Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static ImageCustomer getLastImage(Customer cust) {
        List<ImageCustomer> res = getImages(cust);
        if (res == null)
            return null;
        return res.get(0);
    }

    public static List<ImageCustomer> getImages(Customer cust) {
//        List<ImageCustomer> imageCust = ImageCustomer.find(ImageCustomer.class, "customer = ? "
//                , cust.getId().toString());
        List<ImageCustomer> imageCust = ImageCustomer.find(ImageCustomer.class, "customer = ? "
                , new String[] {cust.getId().toString()}, "", "id desc", "");
        if (imageCust.size() <= 0)
            return null;
        return imageCust;
    }

    public List<ImageCustomer> getImages() {
//        List<ImageCustomer> imageCust = ImageCustomer.find(ImageCustomer.class, "customer = ? "
//                , cust.getId().toString());
        List<ImageCustomer> imageCust = ImageCustomer.find(ImageCustomer.class, "customer = ? "
                , new String[] {getCustomer().getId().toString()}, "", "id desc", "");
        if (imageCust.size() <= 0)
            return null;
        return imageCust;
    }

}
