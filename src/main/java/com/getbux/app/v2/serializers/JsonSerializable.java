/**
 * Copyright 2017 (C) FixStream Networks, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.getbux.app.v2.serializers;

import static com.getbux.app.v2.serializers.JsonSerializers.DESERIALIZER;
import static com.getbux.app.v2.serializers.JsonSerializers.NON_NULL_SERIALIZER;
import static com.getbux.app.v2.serializers.JsonSerializers.SERIALIZER;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public interface JsonSerializable {

	public static <T> T fromJsonObject(Object object, Class<T> classType) {
        return fromJson(toJson(object), classType);
    }
	
    public static <T> T fromJson(String jsonString, Class<T> classType) {
        T obj = null;
        try {
            obj = DESERIALIZER.readValue(jsonString, classType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static <T> List<T> fromJsonList(String jsonString, Class<T> entryObject) {
        List<T> obj = null;
        try {
            obj = DESERIALIZER.readValue(jsonString, DESERIALIZER.getTypeFactory()
                                                                 .constructCollectionType(List.class, entryObject));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        T obj = null;
        try {
            obj = DESERIALIZER.readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static String toJson(Object object, boolean prettyPrint, boolean includeNonNull) {

        ObjectMapper mapper = includeNonNull ? NON_NULL_SERIALIZER
                                             : SERIALIZER;
        return toJson(mapper, object, prettyPrint);
    }

    public static String toJson(ObjectMapper mapper, Object object, boolean prettyPrint) {

        ObjectWriter ow = mapper.writer();
        if (prettyPrint)
            ow = ow.withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object object) {
        return toJson(object, false, false);
    }

    public static String toPrettyJson(Object object) {
        return toJson(object, true, false);
    }

    public static String toNonNullJson(Object object) {
        return toJson(object, false, true);
    }

    default String toJson(boolean prettyPrint, boolean includeNonNull) {
        return toJson(this, prettyPrint, includeNonNull);
    }

    default String toJson() {
        return toJson(false, false);
    }

    default String toPrettyJson() {
        return toJson(true, false);
    }

    default String toNonNullJson() {
        return toJson(false, true);
    }

    public static byte[] toBytes(ObjectMapper mapper, Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(Object object) {
        return toBytes(NON_NULL_SERIALIZER, object);
    }

    public default byte[] toBytes() {
        return toBytes(this);
    }

}
