package mangoo.io.routing;

import io.undertow.util.StatusCodes;

import java.util.HashMap;
import java.util.Map;

import mangoo.io.enums.ContentType;

import org.apache.commons.lang3.StringUtils;
import org.boon.json.JsonFactory;

import com.google.common.base.Charsets;

/**
 *
 * @author svenkubiak
 *
 */
public final class Response {
    private Map<String, Object> content = new HashMap<String, Object>();
    private String redirectTo;
    private String contentType = ContentType.TEXT_HTML.toString();
    private String charset = Charsets.UTF_8.name();
    private String body = "";
    private String template;
    private boolean rendered;
    private boolean redirect;
    private int statusCode;

    /**
     * Creates a response object with HTTP status code 200
     * 
     * @return A response object {@link mangoo.io.routing.Response}
     */
    public static Response withOk() {
        return new Response(StatusCodes.OK);
    }

    /**
     * Creates a response object with HTTP status code 201
     * 
     * @return A response object {@link mangoo.io.routing.Response}
     */
    public static Response withCreated() {
        return new Response(StatusCodes.CREATED);
    }

    /**
     * Creates a response object with HTTP status code 404
     * 
     * @return A response object {@link mangoo.io.routing.Response}
     */
    public static Response withNotFound() {
        return new Response(StatusCodes.NOT_FOUND);
    }

    /**
     * Creates a response object with HTTP status code 401
     * 
     * @return A response object {@link mangoo.io.routing.Response}
     */
    public static Response withForbidden() {
        return new Response(StatusCodes.FORBIDDEN);
    }

    /**
     * Creates a response object with HTTP status code 403
     * 
     * @return A response object {@link mangoo.io.routing.Response}
     */
    public static Response withUnauthorized() {
        return new Response(StatusCodes.UNAUTHORIZED);
    }

    /**
     * Creates a response object with HTTP status code 500
     * 
     * @return A response object {@link mangoo.io.routing.Response}
     */
    public static Response withBadRequest() {
        return new Response(StatusCodes.BAD_REQUEST);
    }

    /**
     * Creates a response object with a given HTTP status code
     * 
     * @param statusCode The status code to set
     * @return A response object {@link mangoo.io.routing.Response}
     */
    public static Response withStatusCode(int statusCode) {
        return new Response(statusCode);
    }

    /**
     * Creates a response object with a given url to redirect to
     * 
     * @param redirectTo The URL to redirect to
     * @return A response object {@link mangoo.io.routing.Response} 
     */
    public static Response withRedirect(String redirectTo) {
        return new Response(redirectTo);
    }

    private Response(int statusCode) {
        this.statusCode = statusCode;
    }

    private Response(String redirectTo) {
        this.redirect = true;
        this.rendered = true;
        this.redirectTo = redirectTo;
    }

    /**
     * Sets a specific template to use for the response
     * 
     * @param template The path to the template (e.g. /mytemplate/template.ftl)
     * @return A response object {@link mangoo.io.routing.Response} 
     */
    public Response andTemplate(String template) {
        if (StringUtils.isBlank(this.template)) {
            this.template = template;
        }
        return this;
    }

    /**
     * Sets a specific content type to use for the response. Default is "text/html"
     * 
     * @param contentType The content type to use
     * @return A response object {@link mangoo.io.routing.Response} 
     */
    public Response andContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Sets a specific charset to the response
     * 
     * @param charset The charset to use
     * @return A response object {@link mangoo.io.routing.Response} 
     */
    public Response andCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * Adds a value to the template that can be accessed using ${name} in the template
     * 
     * @param name The name of the value
     * @param object The actual value
     * @return A response object {@link mangoo.io.routing.Response} 
     */
    public Response andContent(String name, Object object) {
        this.content.put(name, object);
        return this;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getCharset() {
        return this.charset;
    }

    public String getBody() {
        return this.body;
    }

    public String getTemplate() {
        return this.template;
    }

    public Map<String, Object> getContent() {
        return this.content;
    }

    public boolean isRedirect() {
        return this.redirect;
    }

    public boolean isRendered() {
        return this.rendered;
    }

    public String getRedirectTo() {
        return this.redirectTo;
    }

    /**
     * Sets the body of the response. If a body is added, no template rendering will be
     * performed. The default content type "text/html" will be used.
     * 
     * @param body The body for the response
     * @return A response object {@link mangoo.io.routing.Response} 
     */
    public Response andBody(String body) {
        this.body = body;
        this.rendered = true;

        return this;
    }

    /**
     * Converts a given Object to JSON and passing it to the response. If an object is given, no 
     * template rendering will be performed and the content type for the response will be set to
     * "application/json"
     * 
     * @param jsonObject The object to convert to JSON
     * @return A response object {@link mangoo.io.routing.Response} 
     */
    public Response andJsonBody(Object jsonObject) {
        this.contentType = ContentType.APPLICATION_JSON.toString();
        this.body = JsonFactory.create().toJson(jsonObject);
        this.rendered = true;

        return this;
    }

    /**
     * Sets the body of the response. If a body is added, no template rendering will be
     * performed. The content type "text/plain" will be used.
     * 
     * @param text The text for the response
     */
    public Response andTextBody(String text) {
        this.contentType = ContentType.TEXT_PLAIN.toString();
        this.body = text;
        this.rendered = true;

        return this;
    }

    /**
     * Disables template rendering, sending an empty body in the response
     */
    public Response andEmptyBody() {
        this.rendered = true;

        return this;
    }
}