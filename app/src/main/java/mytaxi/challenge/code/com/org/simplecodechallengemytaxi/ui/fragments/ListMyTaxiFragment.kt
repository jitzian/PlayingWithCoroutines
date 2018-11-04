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
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.components.DaggerNetworkComponent
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.module.NetworkModule
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter.ListTaxiPresenter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter.ListTaxiPresenterImpl
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.RestService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.PoiList
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.adapters.RVCustomAdapter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.extensions.requestPermissions
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.view.ListTaxiView
import retrofit2.Retrofit
import java.util.logging.Logger
import javax.inject.Inject

class ListMyTaxiFragment : BaseFragment(), ListTaxiView {

    private var TAG = ListMyTaxiFragment::class.java.simpleName

    private lateinit var mRecyclerViewTaxi: RecyclerView
    private lateinit var rvAdapter: RVCustomAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var listTaxis: MutableList<Taxi>
    private lateinit var listTaxiPresenter: ListTaxiPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context).also {
            log = Logger.getLogger(TAG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState).also {
            rootView = inflater.inflate(R.layout.fragment_list_my_taxi, container, false)
            initView()
            setRecyclerViewItemTouchListener()
        }
        return rootView
    }

    override fun onStart() {
        super.onStart().also {

            launch {
                getAll().let { it ->
                    if (it != null) {
                        listTaxis = it as MutableList<Taxi>
                        displayListOfTaxis(it)
                    }
                }
            }
        }
    }

    override fun initView() {
        //Init Presenter
        context.let {
            listTaxiPresenter = ListTaxiPresenterImpl(it)
        }

        //RecyclerView
        mRecyclerViewTaxi = rootView.findViewById(R.id.mRecyclerViewTaxi)
        mRecyclerViewTaxi.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        mRecyclerViewTaxi.layoutManager = layoutManager
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

                                listTaxis.removeAt(position)
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

    override suspend fun verifyIfDataBaseIsNotEmpty(): Boolean? {
        return listTaxiPresenter.getAll()?.isEmpty()
    }

    override suspend fun insert(taxi: Taxi) {
        listTaxiPresenter.insert(taxi)
    }

    override suspend fun getAll(): List<Taxi>? {
        return listTaxiPresenter.getAll()
    }

    override suspend fun getById(id: Int): Taxi? {
        return listTaxiPresenter.getById(id)
    }

    override fun displayListOfTaxis(lstTaxis: List<Taxi>) {
        rvAdapter = RVCustomAdapter(lstRes = lstTaxis, context = requireContext(), fragmentManager = requireFragmentManager())
        rvAdapter.let {
            requireActivity().runOnUiThread {
                mRecyclerViewTaxi.adapter = rvAdapter
            }
        }
    }

}
