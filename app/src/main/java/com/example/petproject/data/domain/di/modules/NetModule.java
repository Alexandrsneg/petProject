package com.example.petproject.data.domain.di.modules;

import com.example.petproject.common.rest.retrofit.IRestClient;
import com.example.petproject.common.rest.retrofit.RestClient;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;



@Module
public class NetModule {
    public static final String CLIENT_WITHOUT_INTERCEPTOR = "client_without_interceptor";

//    @Provides
//    @Singleton
//    public TokenInterceptor tokenInterceptor(IProfileLocalStorage profileLocalStorage, IRefreshTokenAPI refreshTokenAPI) {
//        return new TokenInterceptor(profileLocalStorage, refreshTokenAPI);
//    }

//    @Provides
//    @Singleton
//    IRefreshTokenAPI refreshTokenAPI(@Named(CLIENT_WITHOUT_INTERCEPTOR) OkHttpClient client, Gson gson) {
//        return new RefreshTokenAPI(client, RestConst.getRestUrl(), gson);
//    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        okClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(tokenInterceptor)
                .addInterceptor(logging);

//        okClientBuilder.retryOnConnectionFailure(false);
        OkHttpClient okHttpClient = okClientBuilder.build();
//        tokenInterceptor.setOkHttpClient(okHttpClient);
        return okHttpClient;
    }

    @Provides
    @Singleton
    @Named(CLIENT_WITHOUT_INTERCEPTOR)
    public OkHttpClient provideOkHttpClientWithOutInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        okClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging);
//        okClientBuilder.retryOnConnectionFailure(false);
        return okClientBuilder.build();
    }

    private static class DateFormatAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

        private final DateFormat format;

        private DateFormatAdapter(DateFormat format) {
            this.format = format;
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override
        public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(format.format(date));
        }

        @Override
        public synchronized Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
            try {
                return format.parse(jsonElement.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }

    @Singleton
    @Provides
    Gson provideGson() {
        final String format = "yyyy-MM-dd HH:mm:ss";
        final JsonDeserializer<Boolean> booleanAdapter = (json, typeOfT, context) -> {
            String value = json.getAsString().toLowerCase().trim();
            return value.equals("true") || value.equals("1");
        };

        return new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Date.class, new DateFormatAdapter(new SimpleDateFormat(format, Locale.US)))
                .registerTypeAdapter(Boolean.class, booleanAdapter)
                .registerTypeAdapter(boolean.class, booleanAdapter)
                .create();
    }

    @Provides
    @Singleton
    public IRestClient provideRestClient(OkHttpClient okHttpClient, Gson gson) {
        return new RestClient(okHttpClient, gson);
    }
}
