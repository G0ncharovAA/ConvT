package ru.gonchar17narod.convt.di.modules

import android.R
import android.widget.ArrayAdapter
import dagger.Module
import dagger.Provides
import ru.gonchar17narod.convt.common.ConvTApplication
import ru.gonchar17narod.convt.mvp.models.Currencies
import java.text.DecimalFormat
import javax.inject.Singleton

@Module
class UiModule(private val convTApplication: ConvTApplication) {


    @Provides
    @Singleton
    fun provideConvTApplication(): ConvTApplication{
        return convTApplication
    }

    @Provides
    fun provideArrayAdapter(convTApplication: ConvTApplication): ArrayAdapter<String>{
        val adapter = ArrayAdapter(convTApplication.applicationContext, R.layout.simple_spinner_item, Currencies.known.clone())
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    @Provides
    @Singleton
    fun provideDecimalFormat(): DecimalFormat{
        return DecimalFormat("#.##")
    }

}