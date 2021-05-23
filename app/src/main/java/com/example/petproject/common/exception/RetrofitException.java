package com.example.petproject.common.exception;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by f0x
 * on 17.10.16.
 * Кастомный эксепшшн что бы выловить боди ответов сервера с 40х кодами
 * честно спер с http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 */

public class RetrofitException extends RuntimeException {
    private final String url;
    private final Response response;
    private final Kind kind;
    private final Retrofit retrofit;
    private final int httpCode;

    RetrofitException(String message, String url, Response response,
                      Kind kind, Throwable exception, Retrofit retrofit, int httpCode) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
        this.httpCode = httpCode;
    }

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.message();
        if (message.isEmpty())
            message = getMessage(response);
        message = response.code() + ": " + message;
        return new RetrofitException(message.trim(), url, response, Kind.HTTP, null, retrofit, response.code());
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.NETWORK, exception, null, 0);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, null, 0);
    }

    public static @NonNull
    String getMessage(Throwable th) {
        if (th instanceof RetrofitException)
            return (getMessage((RetrofitException) th));
        return "Неизвестная ошибка";
    }

    public static @NonNull String getMessage(RetrofitException re) {
        return getMessage(re.response);
    }

    public static @NonNull String getMessage(Response response) {
        ResponseBody errorBody = response.errorBody();
        if (errorBody == null)
            return "Неизвестная ошибка";

        try {

            JsonObject json = new JsonParser().parse(errorBody.string()).getAsJsonObject();
            return json.get("message").getAsString();

        } catch (Exception e) {
            return "Неизвестная ошибка";
        }
    }

    /**
     * The request URL which produced the error.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Response object containing status code, headers, body, etc.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * The event kind which triggered this error.
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * The Retrofit this request was executed on
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified {@code type}.
     */
    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (response == null || response.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(response.errorBody());
    }

    /**
     * Возвращает ХТТП код
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * Identifies the event kind which triggered a {@link RetrofitException}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}