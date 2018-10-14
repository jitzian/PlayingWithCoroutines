package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.components

import android.content.Context
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.TaxiDataBase
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.threadManager.DbWorkerThread
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList
import java.util.logging.Logger

//class DataComponent(private val lstRes: List<PoiList>?, val context: Context){
class DataComponent {
    private var tag: String = DataComponent::class.java.simpleName
    private var log = Logger.getLogger(tag)

    lateinit var mTaxi: Taxi
    private lateinit var mDbWorkerThread: DbWorkerThread
    private var mDb: TaxiDataBase? = null

    fun initDBOnWorkerThread(context: Context){
        log.info("$tag::initDBOnWorkerThread")

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        context.let {
            mDb = TaxiDataBase.getInstance(it)
        }
    }

    fun unwrapData(lstRes: List<PoiList>?){
        log.info("$tag::unwrapData")

        lstRes?.let {
            for(i in lstRes){
                mTaxi = Taxi()
                mTaxi.id = i.id
                mTaxi.fleetType = i.fleetType
                mTaxi.latitude = i.coordinate?.latitude.toString()
                mTaxi.longitude = i.coordinate?.longitude.toString()
                mTaxi.heading = i.heading.toString()
                insertTaxiInDB(mTaxi)
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