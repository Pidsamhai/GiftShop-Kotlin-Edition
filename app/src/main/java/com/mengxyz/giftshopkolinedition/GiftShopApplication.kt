package com.mengxyz.giftshopkolinedition

import android.app.Application
import com.mengxyz.giftshopkolinedition.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GiftShopApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            printLogger()
            androidContext(this@GiftShopApplication)
            modules(myModule)
        }
    }
}