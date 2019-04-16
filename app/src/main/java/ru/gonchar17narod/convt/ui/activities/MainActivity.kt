package ru.gonchar17narod.convt.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import ru.gonchar17narod.convt.R
import ru.gonchar17narod.convt.common.ConvTApplication
import ru.gonchar17narod.convt.mvp.models.Currencies
import ru.gonchar17narod.convt.mvp.presenters.MainPresenter
import ru.gonchar17narod.convt.mvp.views.MainView
import java.text.DecimalFormat
import javax.inject.Inject

class MainActivity : MvpActivity(), MainView {

    init {
        ConvTApplication.uiComponent.inject(this)
    }

    @Inject
    lateinit var fromAdapter: ArrayAdapter<String>
    @Inject
    lateinit var toAdapter: ArrayAdapter<String>
    @Inject
    lateinit var decimalFormat: DecimalFormat

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        spinnerFrom.setAdapter(fromAdapter)
        spinnerTo.setAdapter(toAdapter)


        if (savedInstanceState === null) mainPresenter.restoreSpinnerPositions()


        buttonConvert.setOnClickListener {
          askForResultValue()
        }
    }

    override fun onResume() {

          editAmount.addTextChangedListener(object : TextWatcher {
              override fun afterTextChanged(s: Editable?) {
                  askForResultValue()
              }
              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
          })
        super.onResume()
    }

    private fun askForResultValue(){
        try {
            val baseValue = editAmount.text.toString().toFloat()
            mainPresenter.getConvertResults(
                spinnerFrom.selectedItem as String
                        + "_" +
                        spinnerTo.selectedItem as String,
                baseValue
            )
        } catch (e: Exception) {
            showError(e)
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onValue(value: Float) {
            textResultValue.setText(decimalFormat.format(value))
    }

    override fun showError(error: Throwable) {
        Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
    }

    override fun hideError(error: Throwable) {}

    override fun restoreSpinners(fromPosition: String, toPosition: String) {

        try {
            spinnerFrom.setSelection(Currencies.known.indexOf(fromPosition))
            spinnerTo.setSelection(Currencies.known.indexOf(toPosition))
        } catch (e: Exception) {
            showError(e)
        }
    }

    override fun onDestroy() {
        mainPresenter.saveSpinnerPositions(spinnerFrom.selectedItem as String, spinnerTo.selectedItem as String)
        super.onDestroy()
    }
}
