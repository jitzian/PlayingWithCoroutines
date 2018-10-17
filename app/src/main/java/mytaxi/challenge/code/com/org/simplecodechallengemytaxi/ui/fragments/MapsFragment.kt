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
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.callbacks.FetchDataCallback
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.rest.FetchDataService
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.util.PermissionsUtil
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

        //Validate if user is coming from specific marker to be displayed. If user is coming
        //from NavigationDrawer option, then all the results are going to be displayed, otherwise,
        //just one marker will be displayed and camera will be positioned to that particular marker
        if (arguments == null) {
            fetchData()
        }
        retainInstance = true

        permissionsUtil = PermissionsUtil(context, activity)
        permissionsUtil.requestPermissions()

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
        log.severe("$TAG -> onMapReady")
        mMap = googleMap ?: return
        safeLet(arguments?.getString("latitude")?.toDouble(), arguments?.getString("longitude")?.toDouble()) { lat, long ->

            activity?.runOnUiThread {
                val position = LatLng(lat, long)
                mMap.addMarker(MarkerOptions().position(position).title(id.toString()))
                gotoMarker(position)
            }
        }
    }

    /**
     * Move camera to specific marker
     * **/
    private fun gotoMarker(position: LatLng) {
        with(mMap) {
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
            animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10f))
        }
    }

    /**
     * Once we get notification that data was fetched successfully, then start adding markers on
     * ViewMAP
     * **/
    override fun notifyCallBack(lstRes: List<PoiList>) {
        with(mMap) {
            activity?.runOnUiThread {
                for (i in lstRes) {
                    with(i) {
                        safeLet(coordinate?.latitude, coordinate?.longitude) { lat, long ->
                            val position = LatLng(lat, long)
                            addMarker(MarkerOptions().position(position).title(id.toString()))
                            gotoMarker(position)
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(latitude: String, longitude: String): MapsFragment {
            val args = Bundle()
            args.putString("latitude", latitude)
            args.putString("longitude", longitude)

            val fragment = MapsFragment()
            fragment.arguments = args

            return fragment

        }
    }

}
