package ru.gonchar17narod.convt.di.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule(private val context: Context) {

    private val PREFERENCES = "preferences"

    @Provides
    @Singleton
    fun provideContext(): Context{
        return context
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences{
        return context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
    }


}