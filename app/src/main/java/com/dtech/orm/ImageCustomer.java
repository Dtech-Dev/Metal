package com.dtech.orm;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by aris on 22/09/15.
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

    public ImageCustomer(List<String> imagecustRecord) {
        this.name = imagecustRecord.get(1);
        this.longitude=imagecustRecord.get(2);
        this.latitude = imagecustRecord.get(3);
        //this.image = By
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

    public byte getImage() {
        return image;
    }

    public void setImage(byte image) {
        this.image = image;
    }
}
