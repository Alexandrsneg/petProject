package com.example.petproject.common.rest;

import android.util.Log;

import com.example.petproject.common.exception.ErrorResponse;
import com.example.petproject.common.exception.RepositoryException;
import com.example.petproject.common.exception.RetrofitException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by andreyka
 * on 14.10.2016.
 */

public class BaseRepository {
    public static final int COUNT_TIES = 0;
    public static final int TIMEOUT_SEC = 60;
    protected String tag;
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public BaseRepository() {
        tag = this.getClass().getSimpleName();
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    protected String formatDate(Date date) {
        return date == null ? "" : dateFormat.format(date);
    }

    protected RepositoryException handleError(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof RetrofitException) {
            RetrofitException r = (RetrofitException) throwable;
            if (r.getKind() == RetrofitException.Kind.HTTP) {
                try {
                    if (r.getHttpCode() == 500)
                        return new RepositoryException(ErrorResponse.createBackendError());
                    return new RepositoryException(r.getErrorBodyAs(ErrorResponse.class));
                } catch (IOException e) {
                    Log.e(tag, "Error when parse error body from response", e);
                    return new RepositoryException(ErrorResponse.createBackendError(r.getMessage(), String.valueOf(r.getHttpCode())));
                }
            }
            if (r.getKind() == RetrofitException.Kind.NETWORK) {
                return new RepositoryException(ErrorResponse.createNetworkError());
            }
        }
        return new RepositoryException(ErrorResponse.createBackendError());
    }
}
