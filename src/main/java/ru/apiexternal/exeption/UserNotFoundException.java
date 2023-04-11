package ru.apiexternal.exeption;

public class UserNotFoundException extends IllegalArgumentException{

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
