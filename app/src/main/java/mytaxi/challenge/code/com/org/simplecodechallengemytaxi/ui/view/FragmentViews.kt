package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.view

import com.google.android.gms.maps.model.LatLng
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi

interface BaseView

interface ListMyTaxiFragmentView : BaseView{
    fun setRecyclerViewItemTouchListener()
    fun displayListOfTaxis(lstTaxis: List<Taxi>?)
}

interface MapsFragmentView: BaseView {
    fun placeMarkers(lstTaxis: List<Taxi>)
    fun gotoMarker(position: LatLng)
}

