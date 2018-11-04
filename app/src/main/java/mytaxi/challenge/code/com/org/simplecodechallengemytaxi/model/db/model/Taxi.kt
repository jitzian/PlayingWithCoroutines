package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "taxi")
data class Taxi(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo (name = "id") var id: Int = 0,
        @ColumnInfo (name = "latitude") var latitude: Double? = null,
        @ColumnInfo (name = "longitude") var longitude: Double? = null,
        @ColumnInfo (name = "fleetType") var fleetType: String? = null,
        @ColumnInfo (name = "heading") var heading: Double? = null)
