package com.example.petproject.data.domain.di.components;

import com.example.petproject.App;
import com.example.petproject.data.domain.di.modules.AppModule;
import com.example.petproject.data.domain.di.modules.NetModule;
import com.example.petproject.data.domain.di.modules.RepositoriesModule;
import com.example.petproject.data.domain.di.modules.RestsModules;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NetModule.class,
        AppModule.class,
        RepositoriesModule.class,
        RestsModules.class
})
public interface AppComponent {
    void inject(App app);

    FeedComponent plusFeedComponent();
}

