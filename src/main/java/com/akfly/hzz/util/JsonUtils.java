package com.akfly.hzz.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JsonUtils {

    private JsonUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule("DateTimeModule", Version.unknownVersion());
//        module.addDeserializer(ShortTime.class, new ShortTimeJsonDeserializer());
//        module.addSerializer(ShortTime.class, new ShortTimeJsonSerializer());
//        module.addDeserializer(ShortDate.class, new ShortDateJsonDeserializer());
//        module.addSerializer(ShortDate.class, new ShortDateJsonSerializer());
//        module.addSerializer(Date.class, new DateJsonSerializer());
//        module.addDeserializer(Date.class, new DateJsonDeserializer());
        objectMapper.registerModules(module);
    }

    public static ObjectMapper getObjectMapperInstance() {
        return objectMapper;
    }

    public static String encode(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("encode(Object)", e);
        }
        return null;
    }

    public static <T> T decode(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T decode(String json, TypeReference<T> reference) {
        try {
            return objectMapper.readValue(json, reference);
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e);
        }
        return null;
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(String json, Class<T> cls) {
        try {
            return objectMapper.readValue(json, cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(String json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> jsonArrayToObjectList(String json, Class<T> tClass) {
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
        List<T> ts = null;
        try {
            ts = objectMapper.readValue(json, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ts;
    }

    public static Set<String> commaSeparateStringToSet(String str) {
        if (StringUtils.isBlank(str)) {
            return Sets.newHashSet();
        }
        return Stream.of(str.trim()
                .split("\\s*,\\s*"))
                .collect(Collectors.toSet());
    }


}
