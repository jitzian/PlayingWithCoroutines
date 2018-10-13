package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultRestApi {

    @SerializedName("poiList")
    @Expose
    var poiList: List<PoiList>? = null

}
