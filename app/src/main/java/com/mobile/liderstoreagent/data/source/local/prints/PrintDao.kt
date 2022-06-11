package com.mobile.liderstoreagent.data.source.local.prints
import androidx.room.*


@Dao
interface PrintDao {

    @Query("SELECT * FROM prints WHERE clientId = :clientId")
    fun findPrintByClientId(clientId: Int): PrintEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    /*suspend*/ fun insertPrint(printEntity: PrintEntity)

    @Update
    fun updatePrint(print: PrintEntity)

    @Delete
    /*suspend*/ fun deletePrint(print: PrintEntity)

    @Query("DELETE FROM prints WHERE clientId = :clientId")
    /*suspend*/ fun deletePrintByClientId(clientId: Int)


    @Query("DELETE FROM prints")
    /*suspend*/ fun deleteAll()


    @Query("SELECT EXISTS (SELECT 1 FROM prints WHERE clientId = :clientId)")
    fun exists(clientId: Int): Boolean

}