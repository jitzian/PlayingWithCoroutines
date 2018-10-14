package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest

import android.content.Context
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.callbacks.FetchDataCallback
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.components.DataComponent
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.ResultRestApi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.providers.RetrofitProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

class FetchDataService(fetchDataCallback: FetchDataCallback, val context: Context) : Runnable {
    private var TAG = FetchDataService::class.java.simpleName
    private var log = Logger.getLogger(TAG)
    private var callback: FetchDataCallback = fetchDataCallback
    private lateinit var dataComponent: DataComponent

    override fun run() {
        val restService = RetrofitProvider.getInstance().providesRetrofit().create(RestService::class.java)
        restService.getAllTaxis("53.694865", "9.757589", "53.394655", "10.099891")
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