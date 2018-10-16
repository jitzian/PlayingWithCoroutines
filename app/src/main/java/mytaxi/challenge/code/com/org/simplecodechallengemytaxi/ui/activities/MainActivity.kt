package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.REQUEST_ID_MULTIPLE_PERMISSIONS
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.SPLASH_TIME_OUT
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.cancelText
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.okText
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.yesText
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments.ListMyTaxiFragment
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments.MapsFragment
import java.util.logging.Logger

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var TAG = MainActivity::class.java.simpleName
    val log = Logger.getLogger(TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            Handler().postDelayed({
                // This method will be executed once the timer is over
                // Start your app main activity

                val i = Intent(this@MainActivity, MainActivity::class.java)
                startActivity(i)

                // close this activity
                finish()
            }, SPLASH_TIME_OUT.toLong())
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_list_taxis -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mFrameLayoutMainContainer, ListMyTaxiFragment(), ListMyTaxiFragment::class.java.simpleName)
                        .commit()
            }
            R.id.nav_go_to_maps -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mFrameLayoutMainContainer, MapsFragment(), MapsFragment::class.java.simpleName)
                        .commit()
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun checkAndRequestPermissions(): Boolean {
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val internetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)

        val listPermissionsNeeded = ArrayList<String>()

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (internetPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        log.info("$TAG - ------------------ Permission callback called-------")

        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {

                val perms = HashMap<String, Int>()
                // Initialize the map with both permissions
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.ACCESS_FINE_LOCATION] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.INTERNET] = PackageManager.PERMISSION_GRANTED

                // Fill with actual results from user
                if (grantResults.isNotEmpty()) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    // Check for both permissions
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.INTERNET] == PackageManager.PERMISSION_GRANTED) {
                        log.info("$TAG - sms & location services permission granted")
                        // process the normal flow
                        val i = Intent(this@MainActivity, MainActivity::class.java)
                        startActivity(i)
                        finish()
                        //else any one or both the permissions are not granted
                    } else {
                        log.info("$TAG - Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        //                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {

                            showDialogOK("Service Permissions are required for this app",
                                    DialogInterface.OnClickListener { _, which ->
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                            DialogInterface.BUTTON_NEGATIVE ->
                                                // proceed with logic by disabling the related features or quit the app.
                                                finish()
                                        }
                                    })
                        } else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }//permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                    }
                }
            }
        }

    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(okText, okListener)
                .setNegativeButton(cancelText, okListener)
                .create()
                .show()
    }

    private fun explain(msg: String) {
        val dialog = android.support.v7.app.AlertDialog.Builder(this)
        dialog.setMessage(msg)
                .setPositiveButton(yesText) { _, _ ->
                    //  permissionsclass.requestPermission(type,code);
                    startActivity(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("mytaxi.challenge.code.com.org.simplecodechallengemytaxi")))
                }
                .setNegativeButton(cancelText) { _, _ -> finish() }
        dialog.show()
    }

}
