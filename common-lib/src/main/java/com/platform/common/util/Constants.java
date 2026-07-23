package com.platform.common.util;

/**
 * Global constants for the platform.
 */
public final class Constants {

    /** Default user role. */
    public static final String ROLE_USER = "ROLE_USER";
    /** Admin role. */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /** Authorization header. */
    public static final String AUTH_HEADER = "Authorization";
    /** Bearer prefix for JWT. */
    public static final String AUTH_BEARER_PREFIX = "Bearer ";

    /** User ID header injected by Gateway. */
    public static final String USER_ID_HEADER = "X-Auth-User-Id";
    /** User role header injected by Gateway. */
    public static final String USER_ROLE_HEADER = "X-Auth-User-Role";
    /** Correlation ID header injected by Gateway. */
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    /** Correlation ID MDC variable name. */
    public static final String CORRELATION_ID_LOG_VAR = "correlationId";


    private Constants() {
        // Private constructor for utility class
    }
}
