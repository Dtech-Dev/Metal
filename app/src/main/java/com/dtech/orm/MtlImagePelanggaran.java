package com.dtech.orm;

import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by ADIST on 10/20/2015.
 */
public class MtlImagePelanggaran extends SugarRecord<MtlImagePelanggaran> {
    private MtlPelanggaran foulId; // pelanggaran_id
    private String foulDate; // timestamp
    private String image; // image
    private String imagePath; // file_path
    private String longitude; // longitude
    private String latitude; // latitude

    public MtlImagePelanggaran() {}

    public MtlImagePelanggaran(MtlPelanggaran foulId, String image, String imagePath
            , String latitude, String longitude) {
        this(foulId, null, image, imagePath, longitude, latitude);
        String dateString = DefaultOps.dateToString(Calendar.getInstance().getTime()
                , DefaultOps.DEFAULT_DATETIME_MILI2ND_FORMAT);
        setFoulDate(dateString);
    }

    public MtlImagePelanggaran(MtlPelanggaran foulId, String foulDate, String image
            , String imagePath, String longitude, String latitude) {
        super();
        setFoulId(foulId);
        setFoulDate(foulDate);
        setImage(image);
        setImagePath(imagePath);
        setLongitude(longitude);
        setLatitude(latitude);
    }

    public MtlPelanggaran getFoulId() {
        return foulId;
    }

    public void setFoulId(MtlPelanggaran foulId) {
        this.foulId = foulId;
    }

    public String getFoulDate() {
        return foulDate;
    }

    public void setFoulDate(String foulDate) {
        this.foulDate = foulDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public static MtlImagePelanggaran getLastImage(MtlPelanggaran fouls) {
        List<MtlImagePelanggaran> res = getFoulRecords(fouls);
        if (res.equals(Collections.EMPTY_LIST))
            return null;
        return res.get(0);
    }

    private static List<MtlImagePelanggaran> getFoulRecords(MtlPelanggaran fouls) {
        List<MtlImagePelanggaran> foulRecord = MtlImagePelanggaran
                .find(MtlImagePelanggaran.class, "foul_id = ? "
                        , new String[]{fouls.getId().toString()}, "", "id desc", "");
        if (foulRecord.size() <= 0)
            return Collections.emptyList();
        return foulRecord;
    }
}
