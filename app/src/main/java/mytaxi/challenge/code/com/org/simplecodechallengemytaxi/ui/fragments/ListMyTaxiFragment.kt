package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.adapters.RVCustomAdapter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.callbacks.FetchDataCallback
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.components.DataComponent
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.FetchDataService
import java.util.logging.Logger

class ListMyTaxiFragment : BaseFragment(), FetchDataCallback {

    private var TAG = ListMyTaxiFragment::class.java.simpleName
    private lateinit var mRecyclerViewTaxi: RecyclerView
    private lateinit var rvAdapter: RVCustomAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        log = Logger.getLogger(TAG)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_list_my_taxi, container, false)
        initView()
        return rootView
    }

    override fun onStart() {
        super.onStart()
        fetchData()
    }

    override fun initView() {
        mRecyclerViewTaxi = rootView.findViewById(R.id.mRecyclerViewTaxi)

        //RecyclerView
        mRecyclerViewTaxi.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        mRecyclerViewTaxi.layoutManager = layoutManager
    }

    override fun fetchData() {
        super.fetchData()
        context?.let {
            fetchDataService = FetchDataService(this, it)
            fetchDataService.run()
        }
    }

    override fun notifyCallBack(lstRes: List<PoiList>) {
        log.info("$TAG::notifyCallBack::${lstRes.size}")

        lstRes.let { lst ->
            context?.let {
                rvAdapter = RVCustomAdapter(lst, it)
            }

            rvAdapter.let {
                activity?.runOnUiThread {
                    mRecyclerViewTaxi.adapter = rvAdapter
                }
            }
        }
    }

}
