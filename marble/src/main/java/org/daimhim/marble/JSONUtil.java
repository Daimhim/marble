package org.daimhim.marble;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.LongSerializationPolicy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * author : Zhangx
 * date : 2021/6/3 14:42
 * description :
 */
public class JSONUtil {
    public final static Gson GSON = createGSON(true);

    public final static Gson GSON_NO_NULLS = createGSON(false);

    static Gson createGSON(boolean serializeNulls) {
        GsonBuilder build = new GsonBuilder();
        if (serializeNulls) {
            build.serializeNulls()
                    .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                    .registerTypeAdapter(String.class, new StringGSONAdapter());
        }
        return build.create();
    }

    public static <K, V> Map<K, V> fromMapJson(String json, Class<K> key, Class<V> value) {
        return GSON.fromJson(json, new FromJsonImpl(Map.class, new Type[]{key, value}));
    }


    public static <V> List<V> fromListJson(String json, Class<V> value) {
        return GSON.fromJson(json, new FromJsonImpl(List.class, new Type[]{value}));
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    public static String toJson(Object any, boolean includeNulls) {
        return includeNulls ? GSON.toJson(any) : GSON_NO_NULLS.toJson(any);
    }

    public static String toJson(Object any) {
        return toJson(any, true);
    }

    public static class StringGSONAdapter implements JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null) {
                return "";
            }
            if (json.isJsonObject()) {
                return JSONUtil.toJson(json);
            } else if (json.isJsonArray()) {
                return JSONUtil.toJson(json);
            } else if (json.isJsonPrimitive()){
                return json.getAsString();
            }else {
                return json.toString();
            }
        }
    }

    public static class FromJsonImpl implements ParameterizedType {
        private Class<?> raw;
        private Type[] args;

        public FromJsonImpl(Class<?> raw, Type[] args) {
            this.raw = raw;
            this.args = args;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
