package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.components

import android.content.Context
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.TaxiDataBase
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.threadManager.DbWorkerThread
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList
import java.util.logging.Logger

class DataComponent {
    private var tag: String = DataComponent::class.java.simpleName
    private var log = Logger.getLogger(tag)

    lateinit var mTaxi: Taxi
    internal lateinit var mDbWorkerThread: DbWorkerThread
    private var mDb: TaxiDataBase? = null

    fun initDBOnWorkerThread(context: Context){
        log.info("$tag::initDBOnWorkerThread")

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")

        /**
         * DO NOT FORGET!!!
         * For my future reference: Start and prepareHandler are a MUST in order to avoid crashes!!!
         * **/
        mDbWorkerThread.start()
        mDbWorkerThread.prepareHandler()

        context.let {
            mDb = TaxiDataBase.getInstance(it)
        }
    }

    fun unwrapData(lstRes: List<PoiList>?){
        log.info("$tag::unwrapData")

        lstRes?.let {
            for(i in lstRes){
                with(i){
                    mTaxi = Taxi()
                    mTaxi.id = id
                    mTaxi.fleetType = fleetType
                    mTaxi.latitude = coordinate?.latitude.toString()
                    mTaxi.longitude = coordinate?.longitude.toString()
                    mTaxi.heading = heading.toString()
                    insertTaxiInDB(mTaxi)
                }
            }
        }
    }

    private fun insertTaxiInDB(taxi: Taxi){
        val task = Runnable {
            mDb?.taxiDao()?.insert(taxi)
        }
        mDbWorkerThread.postTask(task)
    }


}