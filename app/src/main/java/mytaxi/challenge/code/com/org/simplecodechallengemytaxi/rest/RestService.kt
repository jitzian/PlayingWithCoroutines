package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.ResultRestApi
import retrofit2.Call
import retrofit2.http.GET

interface RestService{
    @GET("/?p1Lat=53.694865&p1Lon=9.757589&p2Lat=53.394655&p2Lon=10.099891")
    fun getAllTaxis(): Call<ResultRestApi>
}