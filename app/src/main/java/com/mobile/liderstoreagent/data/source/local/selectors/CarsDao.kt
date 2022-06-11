package com.mobile.liderstoreagent.data.source.local.selectors

import androidx.room.*

@Dao
interface CarsDao {
    @Query("SELECT * FROM cars")
    fun getAllCars(): List<CarsEntity>

    @Query("SELECT * FROM cars WHERE name LIKE :carName")
    fun findByCarName(carName: String): CarsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertCars(cars: List<CarsEntity>)

    @Query("DELETE FROM cars")
    /*suspend*/ fun deleteAllCars()
}