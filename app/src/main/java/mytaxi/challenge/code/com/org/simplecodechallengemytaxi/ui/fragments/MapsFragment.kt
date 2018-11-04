package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.experimental.launch
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter.MapsPresenter
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter.MapsPresenterImpl
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.extensions.requestPermissions
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.view.MapsFragmentView
import java.util.logging.Logger

class MapsFragment : BaseFragment(), OnMapReadyCallback, MapsFragmentView {

    private var TAG: String = MapsFragment::class.java.simpleName

    //GoogleMaps
    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var mapsPresenter: MapsPresenter

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState).also {
            mMapView.onSaveInstanceState(outState)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context).also {
            log = Logger.getLogger(TAG)
        }
    }

    override fun placeMarkers(lstTaxis: List<Taxi>) {
        with(mMap) {
            activity?.runOnUiThread {
                for (i in lstTaxis) {
                    with(i) {
                        safeLet(latitude, longitude) { lat, long ->
                            val position = com.google.android.gms.maps.model.LatLng(lat, long)
                            addMarker(MarkerOptions().position(position).title(id.toString()))
                            gotoMarker(position)
                        }
                    }
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState).also {
            retainInstance = true
            requestPermissions()
        }
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
        context.let {
            mapsPresenter = MapsPresenterImpl(it)
        }
        mMapView = rootView.findViewById(R.id.map)
    }

    override fun onResume() {
        super.onResume().also {
            mMapView.onResume()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory().also {
            mMapView.onLowMemory()
        }
    }

    override fun onPause() {
        super.onPause().also {
            mMapView.onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy().also {
            mMapView.onDestroy()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        log.severe("$TAG -> onMapReady")

        mMap = googleMap ?: return

        if (arguments != null){
            safeLet(arguments?.getString("latitude")?.toDouble(), arguments?.getString("longitude")?.toDouble()) { lat, long ->
                activity?.runOnUiThread {
                    val position = LatLng(lat, long)
                    mMap.addMarker(MarkerOptions().position(position).title(id.toString()))
                    gotoMarker(position)
                }
            }
        }else{
            launch {
                safeLet(mapsPresenter.getAll()) { listOfTaxis ->
                    log.severe("$TAG::${listOfTaxis.size}")
                    placeMarkers(listOfTaxis)
                }
            }
        }

    }

    /**
     * Move camera to specific marker
     * **/
    override fun gotoMarker(position: LatLng) {
        with(mMap) {
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
            animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10f))
        }
    }

}
