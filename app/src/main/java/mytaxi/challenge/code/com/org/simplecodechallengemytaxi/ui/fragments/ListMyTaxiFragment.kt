package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.ResultRestApi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.RestService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListMyTaxiFragment : BaseFragment() {
    private var TAG = ListMyTaxiFragment::class.java.simpleName

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        log = Logger.getLogger(TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRetrofit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_list_my_taxi, container, false)
        return rootview
    }

    override fun onStart() {
        super.onStart()
        loadTaxis()
    }

    private fun initRetrofit(){
        restService = retrofit.create(RestService::class.java)
    }

    private fun loadTaxis(){
        restService.getAllTaxis().enqueue(object : Callback<ResultRestApi>{
            override fun onFailure(call: Call<ResultRestApi>, t: Throwable) {
                log.severe("$TAG - ${t.message}")
            }

            override fun onResponse(call: Call<ResultRestApi>, response: Response<ResultRestApi>) {
                log.info("$TAG - ${response.body()}")
            }

        })
    }


}
