package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants

class GlobalConstants{
    companion object {
        //BaseURL EndPoint
        const val baseURL = "https://fake-poi-api.mytaxi.com"

        //Constants for saving RoomDB
        const val dataBaseName = "taxi_database.db"
        const val dataBaseVersion = 1

        const val textSnackBar = "Moving to selected position"

        //Types of taxis
        const val typeTaxi = "TAXI"
        const val typePool = "POOLING"

        //Coordinates provided on the code Challenge
        const val p1Lat = "53.694865"
        const val p1Lon = "9.757589"
        const val p2Lat = "53.394655"
        const val p2Lon = "10.099891"

        //Permissions Constants
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
        const val SPLASH_TIME_OUT = 2000


    }
}