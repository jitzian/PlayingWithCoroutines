package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter

import android.content.Context
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.components.DaggerNetworkComponent
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.module.NetworkModule
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.TaxiDataBase
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.RestService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.PoiList
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.ResultRestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.logging.Logger
import javax.inject.Inject

class ListTaxiPresenterImpl(var context: Context?) : ListTaxiPresenter {
    @Inject
    lateinit var retrofit: Retrofit

    private val TAG = ListTaxiPresenterImpl::class.java.simpleName
    private val log = Logger.getLogger(TAG)
    private var restService: RestService
    private lateinit var lstRes: List<PoiList>
    private lateinit var lstTaxi: List<Taxi>

    private var mDb: TaxiDataBase? = null

    init {
        mDb = context?.let { TaxiDataBase.getInstance(it) }
        DaggerNetworkComponent.builder()
                .networkModule(NetworkModule())
                .build()
                .inject(this)

        restService = retrofit.create(RestService::class.java)
    }

    override fun fetchRemoteData(): List<PoiList>? {
        launch {
            val fetchRemoteDataResult = async {
                restService.getAllTaxis(
                        GlobalConstants.p1Lat,
                        GlobalConstants.p1Lon,
                        GlobalConstants.p2Lat,
                        GlobalConstants.p2Lon).enqueue(object : Callback<ResultRestApi> {
                    override fun onFailure(call: Call<ResultRestApi>, t: Throwable) {
                        log.severe("$TAG::onFailure::${t.message}")
                    }

                    override fun onResponse(call: Call<ResultRestApi>, response: Response<ResultRestApi>) {
                        response.body()?.poiList?.let {
                            lstRes = it
                        }
                    }
                })
            }
            fetchRemoteDataResult.await()
        }

        return lstRes
    }

    override fun insert(taxi: Taxi) {
        launch {
            val insertJob = async {
                mDb?.taxiDao()?.insert(taxi)
            }
            insertJob.await()
        }
    }

    override fun deleteAll() {
        mDb?.taxiDao()?.deleteAll()
    }

    override suspend fun getAll(): List<Taxi>? {
        val getAllJob = launch {
            mDb?.taxiDao()?.getAll()?.let {
                lstTaxi = it
            }
        }
        getAllJob.join()

        return lstTaxi
    }

    override suspend fun getById(id: Int): Taxi? {
        var taxi: Taxi? = null

        val getByIdJob = launch {
            mDb?.taxiDao()?.getById(id)?.let {
                taxi = it
            }
        }
        getByIdJob.join()

        return taxi
    }
}