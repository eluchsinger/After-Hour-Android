package ch.hsr.afterhour.service.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

import ch.viascom.groundwork.foxhttp.parser.FoxHttpParser;

/**
 * Created by eluch on 26.05.2017.
 */

public class CustomGsonParser implements FoxHttpParser {

    private final Gson gson;

    public CustomGsonParser(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public Serializable serializedToObject(String json, Class<Serializable> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public String objectToSerialized(Serializable o) {
        return gson.toJson(o);
    }
}
