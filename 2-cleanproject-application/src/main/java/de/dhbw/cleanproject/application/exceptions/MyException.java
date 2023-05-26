package de.dhbw.cleanproject.application.exceptions;


public class MyException extends Exception {
    private final MyErrorCode code;

    public MyException(MyErrorCode code) {
        this.code = code;
    }

    public MyErrorCode getCode() {
        return this.code;
    }
}
