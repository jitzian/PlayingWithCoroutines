package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi

interface ListTaxiPresenter{
    fun insert(taxi: Taxi)

    fun deleteAll()

    fun getAll(): List<Taxi>?

    fun getById(id: Int): Taxi?
}