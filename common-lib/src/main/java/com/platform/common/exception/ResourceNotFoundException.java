package com.platform.common.exception;

/**
 * Exception for when a requested resource is not found.
 */
public class ResourceNotFoundException extends PlatformException {
    /**
     * Constructs a new exception with the given message.
     *
     * @param messageParam the error message
     */
    public ResourceNotFoundException(final String messageParam) {
        super(messageParam);
    }
}
