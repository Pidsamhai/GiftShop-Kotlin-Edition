package com.mengxyz.giftshopkolinedition

import android.app.Application
import com.mengxyz.giftshopkolinedition.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class GiftShopApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            printLogger()
            androidContext(this@GiftShopApplication)
            modules(myModule)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(object : DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return "${super.createStackElementTag(element)}: ${element.methodName} L:${element.lineNumber}"
                }
            })
        }
    }
}