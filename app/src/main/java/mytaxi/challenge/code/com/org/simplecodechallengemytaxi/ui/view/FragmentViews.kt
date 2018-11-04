package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.view

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi

interface BaseView{
    suspend fun insert(taxi: Taxi)
    suspend fun getAll(): List<Taxi>?
    suspend fun getById(id: Int): Taxi?
}

interface ListMyTaxiFragmentView : BaseView{
    fun setRecyclerViewItemTouchListener()
    fun displayListOfTaxis(lstTaxis: List<Taxi>)
}

interface MapsFragmentView: BaseView

