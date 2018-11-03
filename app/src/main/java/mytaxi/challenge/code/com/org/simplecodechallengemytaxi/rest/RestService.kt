package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model.ResultRestApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService{
    @GET("/")
    fun getAllTaxis(@Query("p1Lat")p1Lat: String,
                    @Query("p1Lon")p1Lon: String,
                    @Query("p2Lat")p2Lat: String,
                    @Query("p2Lon")p2Lon: String): Call<ResultRestApi>
}