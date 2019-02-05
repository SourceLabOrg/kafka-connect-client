package testserver;

public class RequestProperties {
    private final String url;
    private final String requestBody;
    private final String requestMethod;

    public RequestProperties(
        final String url,
        final String requestBody,
        final String requestMethod
    ) {
        this.url = url;
        this.requestBody = requestBody;
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getRequestMethod() {
        return requestMethod;
    }
}
