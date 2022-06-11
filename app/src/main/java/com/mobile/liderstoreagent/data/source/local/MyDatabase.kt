package com.mobile.liderstoreagent.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobile.liderstoreagent.data.source.local.clients.ClientDao
import com.mobile.liderstoreagent.data.source.local.clients.ClientEntity
import com.mobile.liderstoreagent.data.source.local.prints.PrintDao
import com.mobile.liderstoreagent.data.source.local.prints.PrintEntity
import com.mobile.liderstoreagent.data.source.local.selectors.*

@Database(
    entities = [
        ClientEntity::class,
        CarsEntity::class,
        MarketTypeEntity::class,
        TerritoryEntity::class,
        PriceTypeEntity::class,
        PrintEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class MyDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun carsDao(): CarsDao
    abstract fun marketTypeDao(): MarketTypeDao
    abstract fun territoryDao(): TerritoryDao
    abstract fun priceTypeDao(): PriceTypeDao
    abstract fun printDao(): PrintDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}