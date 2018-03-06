package org.sourcelab.kafka.connect.apiclient.request;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.text.SimpleDateFormat;

/**
 * Creates properly configured Jackson XML Mapper instances.
 */
public class JacksonFactory {

    /**
     * Holds our jackson singleton mapper.  ObjectMapper is defined as being
     * ThreadSafe so this should be OK to stash as a static and shared.
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /*
     * Statically configure the instance.
     */
    static {
        // Configure mapper
        mapper
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    /**
     * Creates properly configured Jackson Object Mapper instances.
     * @return ObjectMapper instance.
     */
    public static ObjectMapper newInstance() {
        return mapper;
    }
}
