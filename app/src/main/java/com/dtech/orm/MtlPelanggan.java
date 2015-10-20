package com.dtech.orm;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Administrator on 14/10/2015.
 */
public class MtlPelanggan extends SugarRecord<MtlPelanggan> {
    private String code;
    private String name;
    private String address;

    public MtlPelanggan(){}

    public MtlPelanggan(String code, String name, String address){
        super();
        setCode(code);
        setName(name);
        setAddress(address);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static boolean custExist(String where, String params) {
        if (!where.contains("?"))
            return false;
        List<MtlPelanggan> customer = MtlPelanggan.find(MtlPelanggan.class, where, params);
        return customer.size() > 0;
    }
}
