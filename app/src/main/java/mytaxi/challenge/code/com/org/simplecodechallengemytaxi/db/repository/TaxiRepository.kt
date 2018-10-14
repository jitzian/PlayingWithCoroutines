package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.repository

import android.app.Application
import android.os.AsyncTask
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.dao.TaxiDao
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.TaxiRoomDataBase
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.model.Taxi

class TaxiRepository (application: Application){
    private var taxiDao: TaxiDao
    private var allTaxis: List<Taxi>
    private var db = TaxiRoomDataBase.getDatabase(application)

    init {
        db.let {
            if (it != null) {
                taxiDao = it.taxiDao()
            }
        }
//        val db = TaxiRoomDataBase.getDatabase(application)
        taxiDao = db?.taxiDao()!!
        allTaxis = taxiDao.getAll()
    }

    fun getAllTaxis(): List<Taxi>{
        return allTaxis
    }

    fun insert(taxi: Taxi){
        InsertAsyncTask(taxiDao).execute(taxi)
    }

    private class InsertAsyncTask internal constructor(private val taxiDao: TaxiDao) : AsyncTask<Taxi, Void, Void>() {
        override fun doInBackground(vararg params: Taxi): Void? {
            taxiDao.insert(params[0])
            return null
        }
    }

}