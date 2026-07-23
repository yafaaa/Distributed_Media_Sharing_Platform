package com.platform.common.dto;

/**
 * Standard API response wrapper.
 *
 * @param <T> the type of data enclosed in the response
 */
public final class ApiResponse<T> {

    /** Indicates if the operation was successful. */
    private boolean success;

    /** The message returned by the API. */
    private String message;

    /** The data payload. */
    private T data;

    /**
     * Default constructor.
     */
    public ApiResponse() {
    }

    /**
     * Constructor for full response.
     *
     * @param successParam whether the operation succeeded
     * @param messageParam the message
     * @param dataParam    the data payload
     */
    public ApiResponse(final boolean successParam, final String messageParam,
                       final T dataParam) {
        this.success = successParam;
        this.message = messageParam;
        this.data = dataParam;
    }

    /**
     * Constructor for simple responses (e.g., errors).
     *
     * @param successParam whether the operation succeeded
     * @param messageParam the message
     */
    public ApiResponse(final boolean successParam, final String messageParam) {
        this.success = successParam;
        this.message = messageParam;
    }

    /**
     * Factory method for success response.
     *
     * @param messageParam the message
     * @param dataParam    the data payload
     * @param <T>          the type of data
     * @return the response object
     */
    public static <T> ApiResponse<T> success(
            final String messageParam, final T dataParam) {
        return new ApiResponse<>(true, messageParam, dataParam);
    }

    /**
     * Factory method for error response.
     *
     * @param messageParam the message
     * @param <T>          the type of data
     * @return the response object
     */
    public static <T> ApiResponse<T> error(final String messageParam) {
        return new ApiResponse<>(false, messageParam);
    }

    /**
     * @return whether it is successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param successParam success status
     */
    public void setSuccess(final boolean successParam) {
        this.success = successParam;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param messageParam the message
     */
    public void setMessage(final String messageParam) {
        this.message = messageParam;
    }

    /**
     * @return the data payload
     */
    public T getData() {
        return data;
    }

    /**
     * @param dataParam the data payload
     */
    public void setData(final T dataParam) {
        this.data = dataParam;
    }
}
