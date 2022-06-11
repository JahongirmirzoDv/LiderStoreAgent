package com.mobile.liderstoreagent.data.source.local.selectors

import androidx.room.*

@Dao
interface TerritoryDao {
    @Query("SELECT * FROM territories")
    fun getAllTerritories(): List<TerritoryEntity>

    @Query("SELECT * FROM territories WHERE territory_name LIKE :territoryName")
    fun findByTerritoryName(territoryName: String): TerritoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertTerritories(territories: List<TerritoryEntity>)

    @Query("DELETE FROM territories")
    /*suspend*/ fun deleteAllTerritories()
}