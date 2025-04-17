package org.iot.device.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(InputStream json, Class<T> clazz) throws Exception {
        Reader reader = new InputStreamReader(json, StandardCharsets.UTF_8);
        return gson.fromJson(reader, clazz);
    }
}
