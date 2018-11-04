package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.components.DaggerNetworkComponent
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.module.NetworkModule
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.TaxiDataBase
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.RestService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.PoiList
import retrofit2.Retrofit
import java.util.logging.Logger
import javax.inject.Inject

class MapsPresenterImpl : MapsPresenter {

    override var mDb: TaxiDataBase? = null

    @Inject
    lateinit var retrofit: Retrofit

    private var TAG = MapsPresenterImpl::class.java.simpleName
    private var log = Logger.getLogger(TAG)
    private var restService: RestService

    init {

        DaggerNetworkComponent.builder()
                .networkModule(NetworkModule())
                .build()
                .inject(this)

        restService = retrofit.create(RestService::class.java)

    }


    override fun fetchRemoteData(): List<PoiList>? {
        return null
    }

    override fun insert(taxi: Taxi) {

    }

    override suspend fun getAll(): List<Taxi>? {
        return null
    }

    override suspend fun getById(id: Int): Taxi? {
        return null
    }

    override fun deleteAll() {
    }

}