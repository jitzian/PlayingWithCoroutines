package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.callbacks.FetchDataCallback
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.FetchDataService
import java.util.logging.Logger

class MapsFragment : BaseFragment(), OnMapReadyCallback, FetchDataCallback {
    private var TAG: String = MapsFragment::class.java.simpleName

    //GoogleMaps
    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        log = Logger.getLogger(TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        try {
            rootView = inflater.inflate(R.layout.fragment_maps, container, false)
            initView()

            MapsInitializer.initialize(context)
            mMapView.onCreate(savedInstanceState)
            mMapView.getMapAsync(this)
        } catch (e: InflateException) {
            log.severe("$TAG: ${e.message}")
        }

        return rootView
    }

    override fun initView() {
        mMapView = rootView.findViewById(R.id.map)
    }

    override fun fetchData() {
        super.fetchData()
        context?.let {
            fetchDataService = FetchDataService(this, it)
            fetchDataService.run()
        }
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap ?: return
    }

    override fun notifyCallBack(lstRes: List<PoiList>) {
        with(mMap) {
            activity?.runOnUiThread {
                uiSettings.isZoomControlsEnabled = true
                uiSettings.isMyLocationButtonEnabled = true

                for (i in lstRes) {
                    with(i) {
                        val position = LatLng(coordinate?.latitude!!, coordinate?.longitude!!)
                        addMarker(MarkerOptions().position(position).title(id.toString()))
                    }
                }
                Handler().postDelayed({
                    animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(53.694865, 9.757589), 10f))

                }, 2000)
            }
        }
    }

}
