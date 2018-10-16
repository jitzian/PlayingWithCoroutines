package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.callbacks

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList

/**
 * Interface used to notify (callback) once data is fetched from Network
 * **/
interface FetchDataCallback{
    fun notifyCallBack(lstRes: List<PoiList>)
}