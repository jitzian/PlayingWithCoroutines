package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "taxi")
class Taxi {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0
    var latitude: String? = null
    var longitude: String? = null
    var fleetType: String? = null
    var heading: String? = null
}
