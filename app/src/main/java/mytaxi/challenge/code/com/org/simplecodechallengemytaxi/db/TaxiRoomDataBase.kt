package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.dataBaseName
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.dataBaseVersion
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.dao.TaxiDao
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.model.Taxi

@Database(entities = [Taxi::class], version = dataBaseVersion)
abstract class TaxiRoomDataBase: RoomDatabase() {

    abstract fun taxiDao(): TaxiDao

    companion object {

        @Volatile
        private var INSTANCE: TaxiRoomDataBase? = null

        internal fun getDatabase(context: Context): TaxiRoomDataBase? {
            if(INSTANCE == null){
                synchronized(TaxiRoomDataBase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, TaxiRoomDataBase::class.java, dataBaseName)
                            .build()
                }
            }
            return INSTANCE
        }
    }

}