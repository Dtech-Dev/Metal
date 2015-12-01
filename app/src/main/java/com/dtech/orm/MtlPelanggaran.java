package com.dtech.orm;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Created by ADIST on 10/20/2015.
 */
public class MtlPelanggaran extends SugarRecord {
    @NotNull
    private MtlPelanggan pelanggan; // pelanggan_id
    @NotNull
    private String foulDate; // timestamp
    @NotNull
    private String foulType; // jenis_pelanggaran
    @NotNull
    private String tariff; // tarif
    @NotNull
    private Integer daya; // daya

    /**
     * Mandatory empty constructor
     */
    public MtlPelanggaran() {}

    public MtlPelanggaran(MtlPelanggan pelanggan, String foulDate, String foulType
            , String tariff, Integer daya) {
        this(pelanggan, foulDate, foulType, tariff, daya, false);
    }

    public MtlPelanggaran(MtlPelanggan pelanggan, String foulDate, String foulType
            , String tariff, Integer daya, boolean checkBackwardData) {
        super();
        setPelanggan(pelanggan);
        setFoulType(foulType);
        setFoulDate(foulDate);
        setTariff(tariff);
        setDaya(daya);

        if (checkBackwardData)
            validateBackwardData();
    }

    private void validateBackwardData() {
        return;
        // i dont know yet about this steps, how to manage it?
//        Iterator<Pelanggaran> oldData = Pelanggaran.findAll(Pelanggaran.class);
//        while(oldData.hasNext()) {
//            Pelanggaran currentOldData = oldData.next();
//            // this is means that this data already exist in MtlPelanggan
//            boolean existInLite = custExist("code = ?", currentOldData.getCode());
//            if (!existInLite){
//                MtlPelanggan mtlPelanggan = new MtlPelanggan(currentOldData.getCode(),
//                        currentOldData.getName(), currentOldData.getAddress());
//                mtlPelanggan.setLastXPosition(currentOldData.getLastLatitude());
//                mtlPelanggan.setLastYPosition(currentOldData.getLastLatitude());
//                mtlPelanggan.save();
//            }
//        }
    }

    public MtlPelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(MtlPelanggan pelanggan) {
        this.pelanggan = pelanggan;
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

    public void setFoulDate(String foulDate) {
        this.foulDate = foulDate;
    }

    public Integer getDaya() {
        return daya;
    }

    public void setDaya(Integer daya) {
        this.daya = daya;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public static MtlPelanggaran getLastFoulRecord(MtlPelanggan mtlPelanggan) {
        List<MtlPelanggaran> res = getFoulRecords(mtlPelanggan);
        if (res.equals(Collections.EMPTY_LIST))
            return null;
        return res.get(0);
    }

    private static List<MtlPelanggaran> getFoulRecords(MtlPelanggan mtlPelanggan) {
        List<MtlPelanggaran> foulRecord = MtlPelanggaran.find(MtlPelanggaran.class, "pelanggan = ? "
                , new String[]{mtlPelanggan.getId().toString()}, "", "id desc", "");
        if (foulRecord.size() <= 0)
            return Collections.emptyList();
        return foulRecord;
    }
}
