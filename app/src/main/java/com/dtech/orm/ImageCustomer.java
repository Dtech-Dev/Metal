package com.dtech.orm;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Administrator on 23/09/2015.
 */
public class ImageCustomer extends SugarRecord<ImageCustomer> {
    String name;
    String longitude;
    String latitude;
    byte image;

    public ImageCustomer() {

    }

    public ImageCustomer(String name, String longitude, String latitude, byte image) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.image = image;
    }

    public ImageCustomer(List<String> recordImageCust) {
        this.name = recordImageCust.get(1);
        this.longitude = recordImageCust.get(2);
        this.latitude = recordImageCust.get(3);
    }

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

    public byte getImage() {
        return image;
    }

    public void setImage(byte image) {
        this.image = image;
    }
}
