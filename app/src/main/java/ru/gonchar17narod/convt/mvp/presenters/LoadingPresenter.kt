package ru.gonchar17narod.convt.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.gonchar17narod.convt.api.ApiService
import ru.gonchar17narod.convt.common.ConvTApplication
import ru.gonchar17narod.convt.mvp.models.Currencies
import ru.gonchar17narod.convt.mvp.views.LoadingView
import javax.inject.Inject

@InjectViewState
class LoadingPresenter: MvpPresenter<LoadingView>()  {

    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var apiKey:String


    init {
        ConvTApplication.apiComponent.inject(this)
    }

    fun getCurrencies(){
        viewState.showProgress()
        apiService.getCurrencies(
            apiKey
          )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                Currencies.getCurrencies(result)
                onResult()

            }, {
                    error -> onError(error)
            })
    }


    fun onResult(){
        val k = Currencies.known
        viewState.hideProgress()
        viewState.onResult()
    }

    fun onError(error: Throwable){
        viewState.hideProgress()
        viewState.showError(error)
    }






}