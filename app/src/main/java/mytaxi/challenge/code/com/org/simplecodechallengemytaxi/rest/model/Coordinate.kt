package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coordinate {

    @SerializedName("latitude")
    @Expose
    var latitude: Double = 0.toDouble()
    @SerializedName("longitude")
    @Expose
    var longitude: Double = 0.toDouble()

}
