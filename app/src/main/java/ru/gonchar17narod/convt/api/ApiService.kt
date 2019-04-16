package ru.gonchar17narod.convt.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        val compact = "ultra"
    }

    @GET("api/v7/currencies")
    fun getCurrencies(@Query("apiKey") apiKey: String):Observable<String>

    @GET("/api/v7/convert")
    fun convertCurrency(
        @Query("apiKey") apiKey: String,
        @Query("q") querry: String,
        @Query("compact") compact: String):
            Observable<String>
}