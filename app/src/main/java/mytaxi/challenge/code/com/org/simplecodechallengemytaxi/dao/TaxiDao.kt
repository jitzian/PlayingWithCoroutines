package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.Taxi

interface TaxiDao{
    @Insert
    fun insert(taxi: Taxi)

    @Query("DELETE FROM taxiDetail")
    fun deleteAll()

    @Query("SELECT * FROM taxiDetail ORDER BY id ASC")
    fun getAll(): List<Taxi>

    @Query("SELECT * FROM taxiDetail WHERE id = (:id)")
    fun getById(id: Int): Taxi

}