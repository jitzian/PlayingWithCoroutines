package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.support.v4.app.Fragment
import android.view.View
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.FetchDataService
import java.util.logging.Logger

abstract class BaseFragment : Fragment() {
    protected lateinit var log: Logger
    protected lateinit var rootView: View
    abstract fun initView()

    protected lateinit var fetchDataService: FetchDataService
    open fun fetchData() {}

}