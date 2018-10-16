package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class PermissionsUtil(val context: Context?, private val activityCompat: Activity?) {

    fun requestPermissions() {
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        activityCompat?.let {
            ActivityCompat.requestPermissions(it, permissions, 0)
        }
    }

    private fun isPermissionGranted(permission: String): Boolean =
            context?.let {
                ContextCompat.checkSelfPermission(
                        it,
                        permission
                )
            } == PackageManager.PERMISSION_GRANTED
}