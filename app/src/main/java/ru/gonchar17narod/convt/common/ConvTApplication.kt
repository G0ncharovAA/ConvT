package ru.gonchar17narod.convt.common

import android.app.Application
import ru.gonchar17narod.convt.R
import ru.gonchar17narod.convt.di.components.ApiComponent
import ru.gonchar17narod.convt.di.components.DaggerApiComponent
import ru.gonchar17narod.convt.di.components.DaggerUiComponent
import ru.gonchar17narod.convt.di.components.UiComponent
import ru.gonchar17narod.convt.di.modules.ApiModule
import ru.gonchar17narod.convt.di.modules.PreferencesModule
import ru.gonchar17narod.convt.di.modules.UiModule

class ConvTApplication: Application() {
    companion object {
        lateinit var apiComponent: ApiComponent
        lateinit var uiComponent: UiComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    fun initializeDagger() {
        apiComponent = DaggerApiComponent.builder()
            .apiModule(ApiModule(getString(R.string.baseUrl), getString(R.string.apiKey)))
            .preferencesModule(PreferencesModule(this))
            .build()
        uiComponent = DaggerUiComponent.builder()
            .uiModule(UiModule(this)).
                build()
    }
}