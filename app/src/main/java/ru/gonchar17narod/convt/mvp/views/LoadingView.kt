package ru.gonchar17narod.convt.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface LoadingView: MvpView {

    fun showProgress();

    fun hideProgress();

    fun onResult()

    fun showError(error: Throwable)

    fun hideError(error: Throwable)

}