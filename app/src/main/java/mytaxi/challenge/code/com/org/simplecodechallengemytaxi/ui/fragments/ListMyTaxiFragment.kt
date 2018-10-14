package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.adapters.RVCustomAdapter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.components.DataComponent
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.ResultRestApi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.RestService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.viewmodel.TaxiViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

class ListMyTaxiFragment : BaseFragment() {
    private var TAG = ListMyTaxiFragment::class.java.simpleName
    private lateinit var mRecyclerViewTaxi: RecyclerView
    private lateinit var rvAdapter: RVCustomAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

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
        rootView = inflater.inflate(R.layout.fragment_list_my_taxi, container, false)
        initView()
        return rootView
    }

    override fun onStart() {
        super.onStart()
        loadTaxis()
    }

    private fun initRetrofit(){
        restService = retrofit.create(RestService::class.java)
    }

    private fun loadTaxis(){
        restService.getAllTaxis("53.694865", "9.757589", "53.394655", "10.099891").enqueue(object : Callback<ResultRestApi>{
            override fun onResponse(call: Call<ResultRestApi>, response: Response<ResultRestApi>) {
                log.info("$TAG - ${response.body()?.poiList}")

                context?.let {
                    rvAdapter = RVCustomAdapter(response.body()?.poiList, it)
                    DataComponent(response.body()?.poiList, it)
                }

                rvAdapter.let{
                    activity?.runOnUiThread {
                        mRecyclerViewTaxi.adapter = rvAdapter
                    }
                }

            }

            override fun onFailure(call: Call<ResultRestApi>, t: Throwable) {
                log.severe("$TAG - ${t.message}")
            }
        })
    }

    override fun initView() {
        super.initView()
        mRecyclerViewTaxi = rootView.findViewById(R.id.mRecyclerViewTaxi)

        //RecyclerView
        mRecyclerViewTaxi.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        mRecyclerViewTaxi.layoutManager = layoutManager
    }

    private fun setupViewModel(){
        taxiViewModel = ViewModelProviders.of(this.activity!!).get(TaxiViewModel::class.java)
//        taxiViewModel.getAllTaxis().observe(this, Observer<List<Taxi>> { t -> log.info("$TAG ->> ${t?.size}") })
    }

}
