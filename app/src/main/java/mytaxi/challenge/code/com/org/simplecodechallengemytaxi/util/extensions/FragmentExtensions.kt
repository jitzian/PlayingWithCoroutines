package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.util.extensions

import android.support.v4.app.Fragment

fun Fragment.requestPermissions(){
    val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
    requestPermissions(permissions, 0)
}