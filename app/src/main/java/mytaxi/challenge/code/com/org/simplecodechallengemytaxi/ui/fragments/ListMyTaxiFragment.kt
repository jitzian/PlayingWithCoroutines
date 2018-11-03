package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.adapters.RVCustomAdapter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.components.DaggerNetworkComponent
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.PoiList
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.ResultRestApi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.module.NetworkModule
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.RestService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.extensions.requestPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.logging.Logger
import javax.inject.Inject

class ListMyTaxiFragment : BaseFragment() {

    private var TAG = ListMyTaxiFragment::class.java.simpleName

    private lateinit var mRecyclerViewTaxi: RecyclerView
    private lateinit var rvAdapter: RVCustomAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var listTaxis: MutableList<PoiList>

    @Inject
    lateinit var retrofit: Retrofit

    override fun onAttach(context: Context?) {
        super.onAttach(context).also {
            log = Logger.getLogger(TAG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState).also {
            requestPermissions()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_list_my_taxi, container, false)
        initView()
        setRecyclerViewItemTouchListener()
        return rootView
    }

    override fun onStart() {
        super.onStart().also {
            restService = retrofit.create(RestService::class.java)
        }
        fetchRemoteData()
    }

    private fun fetchRemoteData() {
        launch(UI) {
            val fetchRemoteDataResult = async {
                restService.getAllTaxis(
                    GlobalConstants.p1Lat,
                    GlobalConstants.p1Lon,
                    GlobalConstants.p2Lat,
                    GlobalConstants.p2Lon
                ).enqueue(object : Callback<ResultRestApi> {

                    override fun onFailure(call: Call<ResultRestApi>, t: Throwable) {
                        log.severe("$TAG::onFailure::${t.message}")
                    }

                    override fun onResponse(call: Call<ResultRestApi>, response: Response<ResultRestApi>) {
                        response.body()?.poiList.let { lstResult ->
                            lstResult.let {
                                listTaxis = it as MutableList<PoiList>
                                rvAdapter = RVCustomAdapter(it, requireContext(), requireFragmentManager())
                            }
                            rvAdapter.let {
                                requireActivity().runOnUiThread {
                                    mRecyclerViewTaxi.adapter = rvAdapter
                                }
                            }
                        }
                    }
                })
            }
            fetchRemoteDataResult.await()
        }
    }

    override fun initView() {
        //Dagger Injection
        DaggerNetworkComponent.builder()
            .networkModule(NetworkModule())
            .build().inject(this)

        mRecyclerViewTaxi = rootView.findViewById(R.id.mRecyclerViewTaxi)

        //RecyclerView
        mRecyclerViewTaxi.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        mRecyclerViewTaxi.layoutManager = layoutManager
    }

    /**
     * Setting up Listener for swiping cards. I did not have enough time to implement difference
     * between left or right swiping ='(
     * **/
    private fun setRecyclerViewItemTouchListener() {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    viewHolder1: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    val position = viewHolder.adapterPosition
                    try {
                        listTaxis.removeAt(position)
                        mRecyclerViewTaxi.adapter?.notifyItemRemoved(position)
                    } catch (indexOutOfBoundException: IndexOutOfBoundsException) {
                        log.severe("$TAG:: ${indexOutOfBoundException.message}")
                    }
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerViewTaxi)
    }

}
