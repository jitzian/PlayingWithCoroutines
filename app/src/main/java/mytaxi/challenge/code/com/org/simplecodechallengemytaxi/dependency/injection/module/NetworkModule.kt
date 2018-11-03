package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.module

import dagger.Module
import dagger.Provides
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

@Module
class NetworkModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(GlobalConstants.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build()
    }

}