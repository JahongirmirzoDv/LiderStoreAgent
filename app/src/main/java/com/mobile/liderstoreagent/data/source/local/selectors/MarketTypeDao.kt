package com.mobile.liderstoreagent.data.source.local.selectors

import androidx.room.*

@Dao
interface MarketTypeDao {
    @Query("SELECT * FROM market_type")
    fun getAllMarketTypes(): List<MarketTypeEntity>

    @Query("SELECT * FROM market_type WHERE name LIKE :marketName")
    fun findByMarketName(marketName: String): MarketTypeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertMarketTypes(marketTypes: List<MarketTypeEntity>)

    @Query("DELETE FROM market_type")
    /*suspend*/ fun deleteAllMarketTypes()
}