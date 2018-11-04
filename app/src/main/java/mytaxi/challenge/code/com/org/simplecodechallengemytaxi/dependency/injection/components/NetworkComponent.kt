package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.components

import dagger.Component
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.dependency.injection.module.NetworkModule
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.presenter.ListTaxiPresenterImpl
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments.ListMyTaxiFragment
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments.MapsFragment

@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(mapsFragment: MapsFragment)
    fun inject(listTaxiPresenter: ListTaxiPresenterImpl)
}