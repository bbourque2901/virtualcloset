package com.nashss.se.virtualcloset.exceptions;

public class ClothingNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4410564863772736322L;

    /**
     * Exception with no message or cause.
     */
    public ClothingNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ClothingNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ClothingNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ClothingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
