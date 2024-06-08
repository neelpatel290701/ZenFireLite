package com.example.zenfirelite

import android.app.Application
import com.example.zenfirelite.utils.Prefs

val prefs: Prefs by lazy {
    App.prefs!!
}

class App: Application()
{
    companion object {
        var prefs: Prefs? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Prefs(applicationContext)
    }
}
