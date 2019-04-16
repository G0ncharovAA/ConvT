package ru.gonchar17narod.convt.di.components

import dagger.Component
import ru.gonchar17narod.convt.di.modules.ApiModule
import ru.gonchar17narod.convt.di.modules.PreferencesModule
import ru.gonchar17narod.convt.mvp.presenters.LoadingPresenter
import ru.gonchar17narod.convt.mvp.presenters.MainPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, PreferencesModule::class])
interface ApiComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(loadingPresenter: LoadingPresenter)
}