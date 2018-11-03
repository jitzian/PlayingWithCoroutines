package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter

import android.content.Context
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.TaxiDataBase
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model.Taxi

class ListTaxiPresenterImpl (var context: Context): ListTaxiPresenter{
    private var mDb: TaxiDataBase? = null

    init{
        mDb = TaxiDataBase.getInstance(context)
    }

    override fun insert(taxi: Taxi){
        mDb?.taxiDao()?.insert(taxi)
    }

    override fun deleteAll(){
        mDb?.taxiDao()?.deleteAll()
    }

    override fun getAll(): List<Taxi>? {
        return mDb?.taxiDao()?.getAll()
    }

    override fun getById(id: Int): Taxi? {
        return  mDb?.taxiDao()?.getById(id)
    }
}