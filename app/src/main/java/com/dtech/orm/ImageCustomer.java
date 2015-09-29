package com.dtech.orm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.orm.SugarRecord;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by aris on 22/09/15.
 */

public class ImageCustomer extends SugarRecord<ImageCustomer> {

    private Customer customer;
    private String name;
    private String longitude;
    private String latitude;
    private String imageTest;
    @Deprecated
    byte[] image;

    private static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    private static final int DEFAULT_COMPRESSION = 60;

    public ImageCustomer() {}

    public ImageCustomer(Customer customer, String name, String longitude, String latitude
            , byte[] image, String imageTest) {
        this.setCustomer(customer);
        this.setName(name);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.image = image;
        this.setImageTest(imageTest);
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

    public String getImageTest() {
        return imageTest;
    }

    public void setImageTest(String imageTest) {
        this.imageTest = imageTest;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static ImageCustomer getLastImageRecord(Customer cust) {
        List<ImageCustomer> res = getImageRecords(cust);
        if (res.equals(Collections.EMPTY_LIST))
            return null;
        return res.get(0);
    }

    public static List<ImageCustomer> getImageRecords(Customer cust) {
        List<ImageCustomer> imageCust = ImageCustomer.find(ImageCustomer.class, "customer = ? "
                , new String[]{cust.getId().toString()}, "", "id desc", "");
        if (imageCust.size() <= 0)
            return Collections.emptyList();
        return imageCust;
    }

    public List<ImageCustomer> getImageRecords() {
        List<ImageCustomer> imageCust = ImageCustomer.find(ImageCustomer.class, "customer = ? "
                , new String[] {getCustomer().getId().toString()}, "", "id desc", "");
        if (imageCust.size() <= 0)
            return null;
        return imageCust;
    }

    public static String encodeImage(Bitmap image, Bitmap.CompressFormat compresFormat
            , int compressQuality){
        if (compresFormat == null)
            compresFormat = DEFAULT_COMPRESS_FORMAT;
        if (compressQuality < 60)
            compressQuality = DEFAULT_COMPRESSION;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(compresFormat, compressQuality, stream);
        byte[] imageByteArray = stream.toByteArray();
        // code below still left a log like this shit:
        // -->> W/OpenGLRenderer: Bitmap too large to be uploaded into a texture
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    public static Bitmap decodeImage(String base64ImgString){
        byte[] decodedImage = Base64.decode(base64ImgString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedImage,
                0, decodedImage.length);
    }

}
