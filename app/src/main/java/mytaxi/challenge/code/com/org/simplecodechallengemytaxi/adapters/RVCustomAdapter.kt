package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.adapters

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.R
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.typePool
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.constants.GlobalConstants.Companion.typeTaxi
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.PoiList
import mytaxi.challenge.code.com.org.simplecodechallengemytaxi.ui.fragments.MapsFragment

class RVCustomAdapter(private var lstRes: List<PoiList>?, private val context: Context, private var fragmentManager: FragmentManager?) : RecyclerView.Adapter<RVCustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_taxi, p0, false))
    }

    override fun getItemCount(): Int {
        lstRes.let {
            return it?.size!!
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (lstRes?.get(position)?.fleetType) {
            typePool -> {
                holder.mImageViewTaxi.setImageResource(R.drawable.carpool_taxi)
            }
            typeTaxi -> {
                holder.mImageViewTaxi.setImageResource(R.drawable.normal_taxi)
            }
        }
        holder.mTextViewFleetTypeValue.text = lstRes?.get(position)?.fleetType
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var mImageViewTaxi: ImageView
        lateinit var mTextViewFleetType: TextView
        lateinit var mTextViewFleetTypeValue: TextView

        init {
            itemView.let {
                mImageViewTaxi = it.findViewById(R.id.mImageViewTaxi)
                mTextViewFleetType = it.findViewById(R.id.mTextViewFleetType)
                mTextViewFleetTypeValue = it.findViewById(R.id.mTextViewFleetTypeValue)

                itemView.setOnClickListener { v: View ->
                    val position: Int = adapterPosition

                    Snackbar.make(v, "${GlobalConstants.textSnackBar}:: " +
                            "${lstRes?.get(position)?.coordinate?.latitude.toString()}, ${lstRes?.get(position)?.coordinate?.longitude.toString()}",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show()
                    val gotoMapsFragment = MapsFragment()
                    val bundle = Bundle()

                    bundle.putString("latitude", lstRes?.get(position)?.coordinate?.latitude.toString())
                    bundle.putString("longitude", lstRes?.get(position)?.coordinate?.longitude.toString())

                    gotoMapsFragment.arguments = bundle

                    Handler().postDelayed({
                        fragmentManager?.beginTransaction()?.replace(R.id.mFrameLayoutMainContainer, gotoMapsFragment, MapsFragment::class.java.simpleName)?.commit()
                    }, 500)
                }
            }
        }
    }

}