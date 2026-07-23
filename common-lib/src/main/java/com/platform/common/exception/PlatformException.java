package com.platform.common.exception;

/**
 * Base custom exception for the platform.
 */
public class PlatformException extends RuntimeException {
    /**
     * The error message.
     */
    private final String message;

    /**
     * Constructs a new PlatformException with the given message.
     *
     * @param messageParam the error message
     */
    public PlatformException(final String messageParam) {
        super(messageParam);
        this.message = messageParam;
    }

    /**
     * Gets the error message.
     *
     * @return the message
     */
    @Override
    public final String getMessage() {
        return message;
    }
}
