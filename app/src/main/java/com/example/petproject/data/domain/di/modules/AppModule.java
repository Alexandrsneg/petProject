package com.example.petproject.data.domain.di.modules;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subjects.PublishSubject;

/**
 * Created by f0x on 30.11.16.
 */
@Module
public class AppModule {

    public static final String NAMED_SUBJECT_FILTER_CONTENT_STATE = "NAMED_SUBJECT_FILTER_CONTENT_STATE";

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context getContext() {
        return context;
    }

    @Provides
    @Singleton
    @Named(NAMED_SUBJECT_FILTER_CONTENT_STATE)
    PublishSubject<Boolean> replaySubjectFilterState() {
        return PublishSubject.create();
    }
}
