package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.adapters.RVCustomAdapter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.callbacks.FetchDataCallback
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.FetchDataService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.util.PermissionsUtil
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionsUtil = PermissionsUtil(context, activity)
        permissionsUtil.requestPermissions()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_list_my_taxi, container, false)
        initView()
        setRecyclerViewItemTouchListener()
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

    lateinit var listTaxis: MutableList<PoiList>

    override fun notifyCallBack(lstRes: List<PoiList>) {
        log.info("$TAG::notifyCallBack::${lstRes.size}")
        listTaxis = lstRes.toMutableList()

        lstRes.let { lst ->
            context?.let {
                rvAdapter = RVCustomAdapter(lst, it, fragmentManager)
            }

            rvAdapter.let {
                activity?.runOnUiThread {
                    mRecyclerViewTaxi.adapter = rvAdapter
                }
            }
        }
    }

    /**
     * Setting up Listener for swiping cards. I did not have enough time to implement difference
     * between left or right swiping ='(
     * **/

    private fun setRecyclerViewItemTouchListener() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                listTaxis.removeAt(position)
                mRecyclerViewTaxi.adapter?.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerViewTaxi)
    }

}
