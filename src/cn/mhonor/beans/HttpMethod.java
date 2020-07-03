package cn.mhonor.beans;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author mhonor
 * @version 1.0
 */
public enum HttpMethod {

    /**
     * Request
     */
    REQUEST,

    /**
     * GET
     */
    GET,

    /**
     * POST
     */
    POST,

    /**
     * PUT
     */
    PUT,

    /**
     * DELETE
     */
    DELETE,

    /**
     * PATCH
     */
    PATCH,

    /**
     * HEAD
     */
    HEAD;

    @NotNull
    public static HttpMethod[] getValues() {
        return Arrays.stream(HttpMethod.values()).filter(method -> !method.equals(HttpMethod.REQUEST)).toArray(HttpMethod[]::new);
    }
}
