package com.dtech.orm2;

/**
 * Created by Administrator on 14/10/2015.
 */
public class MtlPelanggan {
    String name;
    String address;

    /*public MtlPelanggan(String name, String address) {
        this.name = name;
        this.address = address;
    }*/
    public MtlPelanggan(){

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
}
