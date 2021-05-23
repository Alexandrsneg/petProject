package com.example.petproject

import android.app.Application
import android.content.Context
import com.example.petproject.data.domain.di.components.AppComponent
import com.example.petproject.data.domain.di.components.DaggerAppComponent
import com.example.petproject.data.domain.di.modules.AppModule
import com.example.petproject.data.domain.di.modules.NetModule
import io.realm.Realm
import io.realm.RealmConfiguration

open class App : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var appComponent: AppComponent
    }

    open fun getContext(): Context? {
        return appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initComponents()
        initRealm()
    }

    open fun getAppComponent(): AppComponent? {
        return appComponent
    }

    private fun initComponents() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this.applicationContext))
            .netModule(NetModule())
            .build()
        appComponent.inject(this)
    }

    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
//            .schemaVersion(Migration.DB_VERSION)
//            .migration(Migration())
            .build()
        )
    }
}