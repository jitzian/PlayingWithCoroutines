package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.TaxiDataBase
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.PoiList

interface BasePresenter{
    var mDb: TaxiDataBase?
}

interface ListTaxiPresenter: BasePresenter {
    fun fetchRemoteData(): List<PoiList>?
    fun insert(taxi: Taxi)
    fun deleteAll()
    suspend fun getAll(): List<Taxi>?
    suspend fun getById(id: Int): Taxi?
}

interface MapsPresenter {
    fun fetchRemoteData(): List<PoiList>?
    fun insert(taxi: Taxi)
    suspend fun getAll(): List<Taxi>?
    suspend fun getById(id: Int): Taxi?
}