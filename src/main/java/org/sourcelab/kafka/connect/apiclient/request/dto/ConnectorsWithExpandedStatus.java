/**
 * Copyright 2018, 2019 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.Collection;
import java.util.Map;

/**
 * Deployed Connectors extended with their associated ConnectorDefinitions.
 *
 * Requires Kafka-Connect server 2.3.0+
 */
public interface ConnectorsWithExpandedStatus {

    /**
     * Names for all deployed connectors.
     * @return Names of all deployed connectors.
     */
    Collection<String> getConnectorNames();

    /**
     * Given a connector name, return the status for the connector.
     * @param connectorName name of connector to return status for.
     * @return ConnectorStatus for the given connector name.
     * @throws IllegalArgumentException if passed a connector name not included in the results.
     */
    ConnectorStatus getStatusForConnector(final String connectorName);

    /**
     * All connector statuses.
     * @return All connector statuses.
     */
    Collection<ConnectorStatus> getAllStatuses();

    /**
     * Map of ConnectorName to its respective Status.
     * @return Map of ConnectorName to its respective Status.
     */
    Map<String, ConnectorStatus> getMappedStatuses();
}
