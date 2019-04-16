package ru.gonchar17narod.convt.mvp.presenters

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.gonchar17narod.convt.api.ApiService
import ru.gonchar17narod.convt.common.ConvTApplication
import ru.gonchar17narod.convt.mvp.models.ConvertResult
import ru.gonchar17narod.convt.mvp.views.MainView
import javax.inject.Inject

@InjectViewState
class MainPresenter: MvpPresenter<MainView>()  {

    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var apiKey:String
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var currentFloatResult: Float = 1.0f
    private val FROM_POSITION = "from position"
    private val TO_POSITION = "to position"
    private var fromPosition = ""
    private var toPosiotion = ""

    init {
        ConvTApplication.apiComponent.inject(this)
    }


    fun getConvertResults(querry: String, baseValue: Float){

        currentFloatResult = baseValue
        if((querry == ConvertResult.lastQuerry) && (!ConvertResult.isOutdated())){
            onResult()
            return
        }

        ConvertResult.lastQuerry = querry
        viewState.showProgress()
        apiService.convertCurrency(
            apiKey,
            querry,
            ApiService.compact)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                ConvertResult.newResultObtained(result)
                onResult()
            }, {
                    error -> onError(error)
            })
    }

    fun onResult(){
        viewState.hideProgress()
        currentFloatResult *= ConvertResult.getValue()
        viewState.onValue(currentFloatResult)
    }

    fun onError(error: Throwable){
        viewState.hideProgress()
        viewState.showError(error)
    }

    fun saveSpinnerPositions(fromPosition: String, toPosiotion:String){
        this.fromPosition = fromPosition
        this.toPosiotion = toPosiotion
    }

    fun restoreSpinnerPositions(){
        if (sharedPreferences.contains(FROM_POSITION) && sharedPreferences.contains(TO_POSITION)) {
            fromPosition = sharedPreferences.getString(FROM_POSITION, "")
            toPosiotion = sharedPreferences.getString(TO_POSITION,"")
        }
        viewState.restoreSpinners(fromPosition, toPosiotion)
    }

    override fun onDestroy() {
        val editor = sharedPreferences.edit()
        editor.putString(FROM_POSITION, fromPosition)
        editor.putString(TO_POSITION, toPosiotion)
        editor.apply()
        super.onDestroy()
    }
}