package org.sourcelab.kafka.connect.apiclient.request.get;

import com.google.common.base.Preconditions;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorStatus;

import java.io.IOException;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines request to get the status of a connector.
 */
public final class GetConnectorStatus implements GetRequest<ConnectorStatus> {

    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of connector.
     */
    public GetConnectorStatus(final String connectorName) {
        Preconditions.checkNotNull(connectorName);
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName) + "/status";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public ConnectorStatus parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorStatus.class);
    }
}
