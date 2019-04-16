package ru.gonchar17narod.convt.di.components

import dagger.Component
import ru.gonchar17narod.convt.di.modules.UiModule
import ru.gonchar17narod.convt.ui.activities.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [UiModule::class])
interface UiComponent {
    fun inject(mainActivity: MainActivity)
}