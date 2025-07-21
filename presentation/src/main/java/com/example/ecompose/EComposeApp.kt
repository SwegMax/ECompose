package com.example.ecompose

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.ecompose.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EComposeApp)
            modules(listOf(
                presentationModule,
                dataModule,
                domainModule
            ))
        }
    }
}