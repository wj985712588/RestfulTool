package cn.mhonor.annotation;


import cn.mhonor.beans.HttpMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author mhonor
 */
@SuppressWarnings("ALL")
public enum JarsHttpMethodAnnotation {

    /**
     * GET
     */
    GET("javax.ws.rs.GET", HttpMethod.GET),
    /**
     * POST
     */
    POST("javax.ws.rs.POST", HttpMethod.POST),
    /**
     * PUT
     */
    PUT("javax.ws.rs.PUT", HttpMethod.PUT),
    /**
     * DELETE
     */
    DELETE("javax.ws.rs.DELETE", HttpMethod.DELETE),
    /**
     * HEAD
     */
    HEAD("javax.ws.rs.HEAD", HttpMethod.HEAD),
    /**
     * PATCH
     */
    PATCH("javax.ws.rs.PATCH", HttpMethod.PATCH);

    private String qualifiedName;
    private HttpMethod method;

    JarsHttpMethodAnnotation(String qualifiedName, HttpMethod method) {
        this.qualifiedName = qualifiedName;
        this.method = method;
    }

    @Nullable
    public static JarsHttpMethodAnnotation getByQualifiedName(String qualifiedName) {
        for (JarsHttpMethodAnnotation springRequestAnnotation : JarsHttpMethodAnnotation.values()) {
            if (springRequestAnnotation.getQualifiedName().equals(qualifiedName)) {
                return springRequestAnnotation;
            }
        }
        return null;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    @NotNull
    public String getShortName() {
        return qualifiedName.substring(qualifiedName.lastIndexOf(".") - 1);
    }
}