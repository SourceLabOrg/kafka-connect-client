package org.sourcelab.kafka.connect.apiclient.request.get;

import com.google.common.base.Preconditions;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;

import java.io.IOException;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines request to get details about a deployed connector.
 */
public final class GetConnector implements GetRequest<ConnectorDefinition> {

    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of the connector.
     */
    public GetConnector(final String connectorName) {
        Preconditions.checkNotNull(connectorName);
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName);
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public ConnectorDefinition parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorDefinition.class);
    }
}
