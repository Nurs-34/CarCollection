package kg.surfit.carcollection.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_table")
data class Car(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "car_name") val carName: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "photo") val photo: String,
    @ColumnInfo(name = "engine_capacity") val engineCapacity: Float,
    @ColumnInfo(name = "date_added") val dateAdded: Long
)

