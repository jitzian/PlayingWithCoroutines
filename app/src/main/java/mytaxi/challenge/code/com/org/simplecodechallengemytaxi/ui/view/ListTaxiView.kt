package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.view

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi

interface ListTaxiView {

    suspend fun insert(taxi: Taxi)
    suspend fun getAll(): List<Taxi>?
    suspend fun getById(id: Int): Taxi?
    suspend fun verifyIfDataBaseIsNotEmpty(): Boolean?

    fun setRecyclerViewItemTouchListener()
    fun displayListOfTaxis(lstTaxis: List<Taxi>)

}