package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.components

import dagger.Component
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.module.NetworkModule
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments.ListMyTaxiFragment
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments.MapsFragment

@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(listMyTaxiFragment: ListMyTaxiFragment)
    fun inject(mapsFragment: MapsFragment)
}