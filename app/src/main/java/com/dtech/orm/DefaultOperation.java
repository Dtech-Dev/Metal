package com.dtech.orm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ADIST on 10/20/2015.
 */
public class DefaultOperation {
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String DEFAULT_DATETIME_MILI2ND_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    public static final int DEFAULT_COMPRESSION = 60;

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