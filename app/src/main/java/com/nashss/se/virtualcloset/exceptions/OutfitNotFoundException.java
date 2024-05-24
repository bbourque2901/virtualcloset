package com.nashss.se.virtualcloset.exceptions;

public class OutfitNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 6256207697557305225L;

    /**
     * Exception with no message or cause.
     */
    public OutfitNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public OutfitNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public OutfitNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public OutfitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
