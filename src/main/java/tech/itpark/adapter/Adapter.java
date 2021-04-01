package tech.itpark.adapter;

import com.google.gson.Gson;

public class Adapter {

    private final Gson gson = new Gson();

    public byte[] transfer(Object obj) {
        if (obj == null || obj instanceof byte[]) {
            return (byte[]) obj;
        } else if (obj instanceof String) {
            return ((String) obj).getBytes();
        } else {
            return gson.toJson(obj).getBytes();
        }
    }

    public <T> T transfer(byte[] bytes, Class<?> type) {
        return (T) gson.fromJson(new String(bytes), type);
    }
}
