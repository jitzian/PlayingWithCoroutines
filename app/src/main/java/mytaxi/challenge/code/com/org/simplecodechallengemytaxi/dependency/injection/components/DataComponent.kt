package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.module.components

import android.content.Context
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.TaxiDataBase
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.threadManager.DbWorkerThread
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.PoiList
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
                    mTaxi.latitude = coordinate?.latitude
                    mTaxi.longitude = coordinate?.longitude
                    mTaxi.heading = heading
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

    private fun insertTaxi(taxi: Taxi) {
        launch {
            val insertTaxiJob = async {
                log.severe("$tag::insertTaxiJob::")
                mDb?.taxiDao()?.insert(taxi)
            }
            insertTaxiJob.await()
        }
    }

}