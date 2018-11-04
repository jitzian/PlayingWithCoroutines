package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi

@Dao
interface TaxiDao{

    @Insert(onConflict = REPLACE)
    fun insert(taxi: Taxi)

    @Query("DELETE FROM taxi")
    fun deleteAll()

    @Query("SELECT * FROM taxi ORDER BY id ASC")
    fun getAll(): List<Taxi>?

    @Query("SELECT * FROM taxi WHERE id = (:id)")
    fun getById(id: Int): Taxi

}