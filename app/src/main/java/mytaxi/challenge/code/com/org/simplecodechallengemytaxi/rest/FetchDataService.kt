package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest

import android.content.Context
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.callbacks.FetchDataCallback
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.components.DataComponent
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.p1Lat
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.p1Lon
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.p2Lat
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.p2Lon
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.ResultRestApi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.providers.RetrofitProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

/***
 * Component that centralizes the fetching of data from Network. Once it is fetched, there is a very
 * super cooll mechanism of storing this data on RoomDataBase using threads.
 *
 * A super important thing is that I'm performing this operation on background thread. Just take a
 * look into line #32.
 * **/

class FetchDataService(fetchDataCallback: FetchDataCallback, val context: Context) : Runnable {
    private var TAG = FetchDataService::class.java.simpleName
    private var log = Logger.getLogger(TAG)
    private var callback: FetchDataCallback = fetchDataCallback
    private lateinit var dataComponent: DataComponent

    override fun run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)
        val restService = RetrofitProvider.getInstance().providesRetrofit().create(RestService::class.java)
        restService.getAllTaxis(p1Lat, p1Lon, p2Lat, p2Lon)
                .enqueue(object : Callback<ResultRestApi> {

                    override fun onFailure(call: Call<ResultRestApi>, t: Throwable) {
                        log.severe("$TAG:: onFailure:: ${t.message}")
                    }

                    override fun onResponse(call: Call<ResultRestApi>, response: Response<ResultRestApi>) {
                        log.info("$TAG::onResponse::SO FAR SO GOOD..!!!")
                        response.body()?.poiList?.let { poiList ->
                            callback.notifyCallBack(poiList)

                            dataComponent = DataComponent()
                            dataComponent.initDBOnWorkerThread(context)
                            dataComponent.unwrapData(poiList)
                            dataComponent.mDbWorkerThread.quit()

                        }
                    }

                })
    }

}