package com.dtech.orm;

import java.util.List;

/**
 * Created by ADIST on 9/18/2015.
 */
public class Customer {
    int _id;
    String _code;
    String _name;
    String _address;
    String _foul_type;
    String _tarif_daya;

    // START : Constructor
    public Customer(){}
    public Customer(int id, String code, String name, String address, String foulType, String tarifDaya) {
        this._id = id;
        this._code = code;
        this._name = name;
        this._address = address;
        this._foul_type = foulType;
        this._tarif_daya = tarifDaya;
    }
    public Customer(String code, String name, String address, String foulType, String tarifDaya) {
        this._code = code;
        this._name = name;
        this._address = address;
        this._foul_type = foulType;
        this._tarif_daya = tarifDaya;
    }
    public Customer(List<String> custRecord){
        this._id = Integer.parseInt(custRecord.get(0));
        this._code = custRecord.get(1);
        this._name = custRecord.get(2);
        this._address = custRecord.get(3);
        this._foul_type = custRecord.get(4);
        this._tarif_daya = custRecord.get(5);
    }
    // END : Constructor

    // START : Set & Get
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_code() {
        return _code;
    }

    public void set_code(String _code) {
        this._code = _code;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_foul_type() {
        return _foul_type;
    }

    public void set_foul_type(String _foul_type) {
        this._foul_type = _foul_type;
    }

    public String get_tarif_daya() {
        return _tarif_daya;
    }

    public void set_tarif_daya(String _tarif_daya) {
        this._tarif_daya = _tarif_daya;
    }
    // END : Set & Get
}
