package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.callbacks

import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList

interface FetchDataCallback{
    fun notifyCallBack(lstRes: List<PoiList>)
}