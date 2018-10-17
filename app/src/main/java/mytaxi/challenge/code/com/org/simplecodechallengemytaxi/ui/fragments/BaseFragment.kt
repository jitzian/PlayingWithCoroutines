package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.support.v4.app.Fragment
import android.view.View
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.FetchDataService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.util.PermissionsUtil
import java.util.logging.Logger

/**
 * Baseclass that encapsulates base needed components
 * **/

abstract class BaseFragment : Fragment() {
    protected lateinit var log: Logger
    protected lateinit var rootView: View
    abstract fun initView()

    protected lateinit var permissionsUtil: PermissionsUtil
    protected lateinit var fetchDataService: FetchDataService

    open fun fetchData() {}

    //This function is to manage 2 parameters with let, so, I'm avoiding var1.let{ it.var2.let{...
    fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
        return if (p1 != null && p2 != null) block(p1, p2) else null
    }
}