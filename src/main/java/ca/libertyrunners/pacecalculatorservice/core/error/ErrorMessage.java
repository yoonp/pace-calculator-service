package ca.libertyrunners.pacecalculatorservice.core.error;

public final class ErrorMessage {

    private ErrorMessage() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ERROR_INVALID_REQUEST = "It requires two inputs";

    public static final String ERROR_INVALID_NUMBER = "It requires positive number";

}
