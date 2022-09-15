package com.nseit.dominos.service;

public class UserExistException extends Throwable {
    public UserExistException(String msg) {
        super(msg);
    }
}
