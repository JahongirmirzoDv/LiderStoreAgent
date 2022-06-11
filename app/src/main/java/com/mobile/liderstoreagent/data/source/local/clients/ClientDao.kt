package com.mobile.liderstoreagent.data.source.local.clients

import androidx.room.*

@Dao
interface ClientDao {

    @Query("SELECT * FROM clients")
    fun getAll(): List<ClientEntity>

    @Query("SELECT * FROM clients WHERE marketName LIKE :marketName")
    fun findByMarketName(marketName: String): ClientEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    /*suspend*/ fun insertClient(clientEntity: ClientEntity)

    @Delete
    fun deleteClient(clientEntity: ClientEntity)

    @Query("DELETE FROM clients")
    /*suspend*/ fun deleteAll()
}