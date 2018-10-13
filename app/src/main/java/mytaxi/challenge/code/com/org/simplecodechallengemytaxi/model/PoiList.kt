package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PoiList {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("coordinate")
    @Expose
    var coordinate: Coordinate? = null
    @SerializedName("fleetType")
    @Expose
    var fleetType: String? = null
    @SerializedName("heading")
    @Expose
    var heading: Double = 0.toDouble()

}
