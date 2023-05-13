package kg.surfit.carcollection.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kg.surfit.carcollection.db.entity.Car

@Dao
interface CarDao {
    @Insert
    fun insertCar(car: Car)

    @Query("SELECT * FROM car_table")
    fun getAllCars(): List<Car>
}