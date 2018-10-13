package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.support.v4.app.Fragment
import android.view.View
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.providers.RetrofitProvider
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.RestService
import retrofit2.Retrofit
import java.util.logging.Logger

abstract class BaseFragment: Fragment() {
    protected lateinit var log: Logger
    protected lateinit var rootview: View
    protected var retrofit: Retrofit = RetrofitProvider.getInstance().providesRetrofit()
    protected lateinit var restService: RestService
    open fun initView(){}
}