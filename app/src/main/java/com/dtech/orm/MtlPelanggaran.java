package com.dtech.orm;

import com.orm.SugarRecord;

import java.math.BigDecimal;

/**
 * Created by ADIST on 10/20/2015.
 */
public class MtlPelanggaran extends SugarRecord<MtlPelanggaran> {
    private MtlPelanggan pelanggan; // pelanggan_id
//    private String name;
    private String foulDate; // timestamp
    private String foulType; // jenis_pelanggaran
    private String tariff; // tarif
    private BigDecimal daya; // daya
//    private String imageTest; // TODO maybe we need it later for last image taken

    public MtlPelanggaran() {}

    public MtlPelanggaran(MtlPelanggan pelanggan, String foulDate, String foulType, String tariff, BigDecimal daya) {
        super();
        setPelanggan(pelanggan);
//        setName(name);
        setFoulType(foulType);
        setFoulDate(foulDate);
        setTariff(tariff);
        setDaya(daya);
    }

    public MtlPelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(MtlPelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

   /* public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

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

    public BigDecimal getDaya() {
        return daya;
    }

    public void setDaya(BigDecimal daya) {
        this.daya = daya;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }
}
