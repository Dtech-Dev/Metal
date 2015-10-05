package com.dtech.orm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.orm.SugarRecord;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by aris on 22/09/15.
 */

public class ImageCustomer extends SugarRecord<ImageCustomer> {

    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private Customer customer;
    private String name;
    private String longitude;
    private String latitude;
    private String imageTest;
    private String foulType;
    private String foulDate;
    private BigDecimal daya;

    private static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    private static final int DEFAULT_COMPRESSION = 60;

    public ImageCustomer() {}

    public ImageCustomer(Customer customer, String name, String longitude, String latitude
            , String imageTest, String foulType, String foulDate, BigDecimal daya) {
        this.setCustomer(customer);
        this.setName(name);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setImageTest(imageTest);
        this.setFoulType(foulType);
        this.setFoulDate(foulDate);
        this.setDaya(daya);
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
                , new String[]{getCustomer().getId().toString()}, "", "id desc", "");
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

    public String getFoulType() {
        return foulType;
    }

    public void setFoulType(String foulType) {
        this.foulType = foulType;
    }

    public String getFoulDate() {
        return foulDate;
    }

    public void setFoulDate(Date foulDate) {
        this.foulDate = dateToString(foulDate, DEFAULT_DATETIME_FORMAT);
    }

    public void setFoulDate(Calendar foulDate) {
        this.foulDate = dateToString(foulDate, DEFAULT_DATETIME_FORMAT);
    }

    public void setFoulDate(String foulDate) {
        this.foulDate = foulDate;
    }

    public BigDecimal getDaya() {
        return daya;
    }

    public void setDaya(BigDecimal daya) {
        this.daya = daya;
    }

    public static String dateToString(Date date, String format) {
        if (format == null || format.length() <= 0)
            format = DEFAULT_DATETIME_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public String dateToString(Calendar calendar, String format) {
        return dateToString(calendar.getTime(), format);
    }
}
