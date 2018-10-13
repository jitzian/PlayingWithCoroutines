package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import java.util.logging.Logger

class MapsFragment : BaseFragment(), OnMapReadyCallback {
    private var TAG = MapsFragment::class.java.simpleName
    private lateinit var mMap: GoogleMap
    private var mapFragment: SupportMapFragment? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        log = Logger.getLogger(TAG)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maps, container, false)
        initView()

        return rootView
    }

    override fun initView() {
        super.initView()
        fragmentManager?.let {
            mapFragment = it.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(this)
        }


    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap.let {
            mMap = it!!
            // Add a marker in Sydney and move the camera
            log.severe("$TAG - Its ready")
            val sydney = LatLng(-34.0, 151.0)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }

    }


}
