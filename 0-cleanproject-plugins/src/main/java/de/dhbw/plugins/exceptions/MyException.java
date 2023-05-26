package de.dhbw.plugins.exceptions;


public class MyException extends Exception {
    private final MyErrorCode code;

    public MyException(MyErrorCode code) {
        this.code = code;
    }

    public MyErrorCode getCode() {
        return this.code;
    }
}
