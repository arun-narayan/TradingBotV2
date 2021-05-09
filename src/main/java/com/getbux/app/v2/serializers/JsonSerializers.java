/**
 * Copyright 2017 (C) FixStream Networks, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.getbux.app.v2.serializers;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JsonSerializers {

    public static final ObjectMapper SERIALIZER =
            new ObjectMapper().registerModule(new JodaModule())
                              .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//                              .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    public static final ObjectMapper NON_NULL_SERIALIZER =
            new ObjectMapper().registerModule(new JodaModule())
                              .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                                         true)
                              .setSerializationInclusion(Include.NON_NULL);

    public static final ObjectMapper DESERIALIZER =
            new ObjectMapper().configure(MapperFeature.USE_GETTERS_AS_SETTERS, false)
                              .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                              .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                              .registerModule(new JodaModule());

}
