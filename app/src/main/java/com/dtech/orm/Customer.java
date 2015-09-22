package com.dtech.orm;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by ADIST on 9/18/2015.
 */
public class Customer extends SugarRecord<Customer> {
    //    int id;
    String code;
    String name;
    String address;
    String foultype;
    String tarifdaya;

    // START : Constructor
    public Customer(){}
    //    public Customer(int id, String code, String name, String address, String foulType, String tarifDaya) {
//        this.id = id;
//        this.code = code;
//        this.name = name;
//        this.address = address;
//        this.foultype = foulType;
//        this.tarifdaya = tarifDaya;
//    }
    public Customer(String code, String name, String address, String foulType, String tarifDaya) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.foultype = foulType;
        this.tarifdaya = tarifDaya;
    }
    public Customer(List<String> custRecord){
//        this.id = Integer.parseInt(custRecord.get(0));
        this.code = custRecord.get(1);
        this.name = custRecord.get(2);
        this.address = custRecord.get(3);
        this.foultype = custRecord.get(4);
        this.tarifdaya = custRecord.get(5);
    }
    // END : Constructor

    // START : Set & Get
//    public int getid() {
//        return id;
//    }
//
//    public void setid(int id) {
//        this.id = id;
//    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getfoultype() {
        return foultype;
    }

    public void setfoultype(String foultype) {
        this.foultype = foultype;
    }

    public String gettarifdaya() {
        return tarifdaya;
    }

    public void settarifdaya(String tarifdaya) {
        this.tarifdaya = tarifdaya;
    }
    // END : Set & Get
}
