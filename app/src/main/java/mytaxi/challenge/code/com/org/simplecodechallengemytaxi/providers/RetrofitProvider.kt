package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.providers

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class RetrofitProvider{

    companion object {
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