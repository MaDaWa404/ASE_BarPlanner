package de.dhbw.plugins;

public class ErrorMessage {

    private final String message;

    private final boolean alert;

    public ErrorMessage(String message, boolean alert) {
        this.message = message;
        this.alert = alert;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAlert() {
        return alert;
    }
}
