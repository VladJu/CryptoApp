package com.example.cryptoapp.presentation

import android.app.Application
import com.example.cryptoapp.di.DaggerApplicationComponent

class CoinApp : Application() {

  // создаем ссылку на компонент

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}