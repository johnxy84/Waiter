package com.frimondi.restaurant.restaurant.Services;


import android.content.Context;

import com.frimondi.restaurant.restaurant.Utils.RestConstant;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * CLient to manage the ext services
 * Created by Lawrence Agbani
 */
public class ServiceClient {
    private static ServiceClient instance;

    private Map<String, RestAdapter> mRestAdapters = new HashMap<String, RestAdapter>();

    private Map<String, Object> mClients = new HashMap<String, Object>();

    Gson gson;

    private ServiceClient() {
        gson = new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        String dateString = json.getAsString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
                        Date date;
                        try {
                            date = sdf.parse(dateString);
                        } catch (ParseException e) {
                            date = null;
                        }
                        return date;
                    }
                })
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
                        String date = sdf.format(src);
                        return new JsonPrimitive(date);
                    }
                })
                .create();
    }


    public static ServiceClient getInstance() {
        if (null == instance) {
            instance = new ServiceClient();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getClient(Context context, Class<T> clazz, String domainPath) {
        RestAdapter mRestAdapter = mRestAdapters.get(domainPath);
        if (mRestAdapter == null) {
            // create interceptor for header
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("app-key", RestConstant.APP_KEY);
                }
            };

            mRestAdapter = new RestAdapter.Builder()
                    .setEndpoint(getBaseUrl(context, domainPath))
                    .setRequestInterceptor(requestInterceptor)
                    .setConverter(new GsonConverter(gson))
                    .build();
            mRestAdapters.put(domainPath, mRestAdapter);
        }
        T client = null;
        String clientKey = domainPath + ":" + clazz.getCanonicalName();
        if ((client = (T) mClients.get(clientKey)) != null) {
            return client;
        }
        client = mRestAdapter.create(clazz);
        mClients.put(clientKey, client);
        return client;
    }


    public String getBaseUrl(Context context, String domainPath) {
        // TODO: switch base url by some sort of settings logic
        return domainPath;
    }
}
