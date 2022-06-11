package com.mobile.liderstoreagent.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.mobile.liderstoreagent.data.source.local.MyDatabase
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mazenrashed.printooth.Printooth
import com.nabinbhandari.android.permissions.BuildConfig
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance =  this
        TokenSaver.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        MyDatabase.getDatabase(this)
        Printooth.init(this)
        AndroidThreeTen.init(this)
    }
    override fun attachBaseContext(base: Context) {
        MultiDex.install(base)
        super.attachBaseContext(base)
    }

    companion object {
        lateinit var instance: App private set
    }
}