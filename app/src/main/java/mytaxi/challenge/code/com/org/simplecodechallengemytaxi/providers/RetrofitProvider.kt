package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.providers

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

/**
 * Singleton Component to provide Retrofit Instance. There is no need of using Dagger2 XD
 * Singleton is ThreadSafe
 ***/

class RetrofitProvider{

    companion object {
        @Volatile
        private var mInstance: RetrofitProvider = RetrofitProvider()

        @Synchronized
        fun getInstance():RetrofitProvider{
            return mInstance
        }
    }

    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .baseUrl(GlobalConstants.baseURL)
                .build()
    }
}