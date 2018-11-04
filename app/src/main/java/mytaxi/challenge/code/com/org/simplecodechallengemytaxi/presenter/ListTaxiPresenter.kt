package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.PoiList

interface ListTaxiPresenter {
    fun fetchRemoteData(): List<PoiList>?
    fun insert(taxi: Taxi)
    fun deleteAll()
    suspend fun getAll(): List<Taxi>?
    suspend fun getById(id: Int): Taxi?
}