package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.support.v4.app.Fragment
import android.view.View
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.providers.RetrofitProvider
import retrofit2.Retrofit
import java.util.logging.Logger

abstract class BaseFragment: Fragment() {
    lateinit var log: Logger
    lateinit var rootview: View
    var retrofit: Retrofit = RetrofitProvider.getInstance().providesRetrofit()
    fun initView(){}
}