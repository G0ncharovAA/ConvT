package ru.gonchar17narod.convt.mvp.models

import org.json.JSONObject

class ConvertResult {
    companion object {
        var lastQuerry: String = ""
        var lastStringResult: String = ""
        var resultLastUpdated: Long = System.currentTimeMillis()
        val millisInHour = 1000 * 60 * 60

        fun getValue(): Float{
            val jsonResult = JSONObject(lastStringResult)
            val key = jsonResult.keys().next()
            val value = jsonResult.getString(key).toFloat()
            return value
        }

        fun newResultObtained(result: String){
            lastStringResult = result
            resultLastUpdated = System.currentTimeMillis()
        }

        fun isOutdated():Boolean{
            return ((System.currentTimeMillis() - resultLastUpdated) > millisInHour)
        }
    }
}