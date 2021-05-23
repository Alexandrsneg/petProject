package com.example.petproject.data.domain.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by f0x on 30.11.16.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
