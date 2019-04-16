package ru.gonchar17narod.convt.mvp.models

import org.json.JSONObject

class Currencies {
    companion object{

        lateinit var known: Array<String>

        fun getCurrencies(string: String):Array<String>{
            val jsonObject = JSONObject(string)
            val jsonResults = jsonObject.getJSONObject("results")

            val currenciesList =  mutableListOf<String>()
            val keysIterator = jsonResults.keys()
            while (keysIterator.hasNext()){currenciesList.add(keysIterator.next().toString())}
            val currencies = currenciesList.toTypedArray()

            known = currencies
            return currencies
        }
    }
}