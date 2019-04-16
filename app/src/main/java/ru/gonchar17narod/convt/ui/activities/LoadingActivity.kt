package ru.gonchar17narod.convt.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_loading.*
import ru.gonchar17narod.convt.R
import ru.gonchar17narod.convt.mvp.presenters.LoadingPresenter
import ru.gonchar17narod.convt.mvp.views.LoadingView

class LoadingActivity: MvpActivity(), LoadingView{

    @InjectPresenter
    lateinit var loadingPresenter: LoadingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        if(savedInstanceState === null)loadingPresenter.getCurrencies()
    }

    override fun showProgress() {
        progressLoadingBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressLoadingBar.visibility = View.INVISIBLE
    }

    override fun onResult() {

        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(intent.flags.or(Intent.FLAG_ACTIVITY_NO_HISTORY))
        startActivity(intent)
        finish()
    }

    override fun showError(error: Throwable) {
        Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun hideError(error: Throwable) {}
}