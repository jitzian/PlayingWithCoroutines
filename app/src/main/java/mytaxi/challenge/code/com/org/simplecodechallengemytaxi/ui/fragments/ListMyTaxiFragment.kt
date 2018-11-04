package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.experimental.launch
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter.ListTaxiPresenter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter.ListTaxiPresenterImpl
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.adapters.RVCustomAdapter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.view.ListMyTaxiFragmentView
import java.util.logging.Logger

class ListMyTaxiFragment : BaseFragment(), ListMyTaxiFragmentView {

    private var TAG = ListMyTaxiFragment::class.java.simpleName

    private lateinit var mRecyclerViewTaxi: RecyclerView
    private lateinit var rvAdapter: RVCustomAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private var listTaxis: MutableList<Taxi>? = null
    private lateinit var listTaxiPresenter: ListTaxiPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context).also {
            log = Logger.getLogger(TAG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState).also {
            //Init Presenter
            context.let { ctx ->
                listTaxiPresenter = ListTaxiPresenterImpl(ctx)
            }

            //Check if DB is not Empty
            launch {
                listTaxiPresenter.getAll().let { lstResult ->
                    if (lstResult != null) {
                        listTaxis = lstResult as MutableList<Taxi>
                    }
                }
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_list_my_taxi, container, false)
        initView()
        setRecyclerViewItemTouchListener()

        return rootView
    }

    override fun onResume() {
        super.onResume().also {
            log.info("$TAG::onResume")
            launch {
                listTaxiPresenter.getAll()?.let { resultDB ->
                    if (resultDB.isNotEmpty()) {
                        log.info("$TAG::onResume::NOT_EMPTY::${resultDB.size}")
                        displayListOfTaxis(resultDB)
                    } else {
                        log.info("$TAG::onResume::EMPTY::$resultDB")
                        displayListOfTaxis(listTaxiPresenter.fetchRemoteData())
                    }
                }
            }
        }
    }

    override fun initView() {
        //RecyclerView
        mRecyclerViewTaxi = rootView.findViewById(R.id.mRecyclerViewTaxi)
        mRecyclerViewTaxi.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        mRecyclerViewTaxi.layoutManager = layoutManager
    }


    override fun displayListOfTaxis(lstTaxis: List<Taxi>?) {
        log.info("$TAG::displayListOfTaxis::$lstTaxis")
        safeLet(lstTaxis, context) { lst, ctx ->
            rvAdapter = RVCustomAdapter(lst, ctx, fragmentManager)
            rvAdapter.let {
                requireActivity().runOnUiThread {
                    mRecyclerViewTaxi.adapter = rvAdapter
                }
            }
        }
    }

    /**
     * Setting up Listener for swiping cards. I did not have enough time to implement difference
     * between left or right swiping ='(
     * **/
    override fun setRecyclerViewItemTouchListener() {
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
                        listTaxis.let {
                            try {

                                listTaxis?.removeAt(position)
                                mRecyclerViewTaxi.adapter?.notifyItemRemoved(position)
                            } catch (indexOutOfBoundException: IndexOutOfBoundsException) {
                                log.severe("$TAG:: ${indexOutOfBoundException.message}")
                            }
                        }
                    }
                }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerViewTaxi)
    }
}
