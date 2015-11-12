package com.dtech.orm;

/**
 * Created by ADIST on 11/12/2015.
 */
public class DuplicateException extends RuntimeException{
    public DuplicateException() {}
    public DuplicateException(String messages){
        super(messages);
    }
}
