package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.dao.TaxiDao
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi

/**
 * Definition of RoomDatabase
 * Singleton thread Safe!!! =D
 * **/

@Database(entities = [Taxi::class], exportSchema = false, version = GlobalConstants.dataBaseVersion)
abstract class TaxiDataBase : RoomDatabase() {

    abstract fun taxiDao(): TaxiDao

    companion object {

        @Volatile
        private var INSTANCE: TaxiDataBase? = null

        fun getInstance(context: Context): TaxiDataBase? {
            if (INSTANCE == null) {
                synchronized(TaxiDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            TaxiDataBase::class.java, GlobalConstants.dataBaseName)
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}