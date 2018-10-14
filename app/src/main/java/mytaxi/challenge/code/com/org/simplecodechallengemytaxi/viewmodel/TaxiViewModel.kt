package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.repository.TaxiRepository
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.db.model.Taxi

class TaxiViewModel(application: Application) : AndroidViewModel(application) {
    private var mTaxiRepository: TaxiRepository = TaxiRepository(application)
    private lateinit var allTaxis: List<Taxi>

    init{
//        mTaxiRepository = TaxiRepository(application)
//        allTaxis = mTaxiRepository?.getAllTaxis()!!

        mTaxiRepository.let { repo->
            repo.getAllTaxis().let {
                allTaxis = it
            }
        }

    }

    fun getAllTaxis(): List<Taxi>{
        return allTaxis
    }

    fun insert(taxi: Taxi){
        mTaxiRepository?.insert(taxi)
    }

}