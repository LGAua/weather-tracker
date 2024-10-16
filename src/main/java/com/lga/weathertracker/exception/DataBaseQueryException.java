package com.lga.weathertracker.exception;

public class DataBaseQueryException extends RuntimeException{
    public DataBaseQueryException(String msg) {
        super(msg);
    }
}
