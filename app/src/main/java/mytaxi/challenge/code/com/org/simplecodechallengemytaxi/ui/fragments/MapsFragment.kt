package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import java.util.logging.Logger

class MapsFragment : BaseFragment(), OnMapReadyCallback {
    private var TAG: String = MapsFragment::class.java.simpleName
    private var mapFragment: SupportMapFragment? = null

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maps, container, false)
        initView()

        MapsInitializer.initialize(context)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)

        return rootView
    }

    override fun initView() {
        super.initView()
//        fragmentManager?.let {
//            mapFragment = it.findFragmentById(R.id.map) as SupportMapFragment?
//            mapFragment?.getMapAsync(this)
//        }

        mMapView = rootView.findViewById(R.id.map)

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
        log.severe("onMapReady::$TAG")


//        googleMap.let {
//            mMap = it!!
//            // Add a marker in Sydney and move the camera
//            log.severe("$TAG - Its ready")
//            val sydney = LatLng(-34.0, 151.0)
//            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        }

    }


}
