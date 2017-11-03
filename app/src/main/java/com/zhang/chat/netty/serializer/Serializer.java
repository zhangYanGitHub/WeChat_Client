package com.zhang.chat.netty.serializer;

import com.google.gson.Gson;

public class Serializer {

    private static Gson gson;

    private static Gson getGson() {
        if (gson == null) {
            synchronized (Serializer.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static String serialize(Object object) {
        return getGson().toJson(object);
    }

    /**
     * 反序列化
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }
}
