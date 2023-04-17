package de.dhbw.plugins;

public class ErrorMessage {

    public static final ErrorMessage registerBarFirst = new ErrorMessage("Please register a bar first", true);
    public static final ErrorMessage notRegistered = new ErrorMessage("not registered", false);
    public static final ErrorMessage notRegisteredAlert = new ErrorMessage("not registered", true);
    public static final ErrorMessage noDataProvided = new ErrorMessage("no data provided", true);
    public static final ErrorMessage alreadyExists = new ErrorMessage("already exists", true);
    public static final ErrorMessage notFound = new ErrorMessage("not found", false);
    public static final ErrorMessage alreadyHaveBar = new ErrorMessage("You already have a bar", true);


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
