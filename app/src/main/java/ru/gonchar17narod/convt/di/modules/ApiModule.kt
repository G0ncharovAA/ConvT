package ru.gonchar17narod.convt.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.gonchar17narod.convt.api.ApiService
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule(val baseUrl: String, val apiKey: String) {


    @Provides
    @Singleton
    fun provideApiKey(): String{
        return apiKey
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    @Named("Gson Converter")
    fun provideRetrofitGson(
                         gson: Gson,
                        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("Scalar Converter")
    fun provideRetrofitScalar(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAppleApiService(@Named("Scalar Converter") retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }
}