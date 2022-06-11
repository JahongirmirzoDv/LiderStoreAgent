package com.mobile.liderstoreagent.data.source.local.selectors

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PriceTypeDao {
    @Query("SELECT * FROM price_type")
    fun getAllPriceTypes(): List<PriceTypeEntity>

    @Query("SELECT * FROM price_type WHERE type LIKE :type")
    fun findByPriceType(type: String): PriceTypeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceTypes(priceTypes: List<PriceTypeEntity>)

    @Query("DELETE FROM price_type")
    /*suspend*/ fun deleteAllPriceTypes()
}