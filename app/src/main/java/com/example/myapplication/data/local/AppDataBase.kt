package com.example.myapplication.data.local

import com.example.myapplication.Converters
import com.example.myapplication.InfracaoDao
import com.example.myapplication.InfracaoEntity
import com.example.myapplication.VeiculoDao
import com.example.myapplication.VeiculoEntity


import android.content.Context
import androidx.room.*

@Database(entities = [InfracaoEntity::class, VeiculoEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun infracaoDao(): InfracaoDao
    abstract fun veiculoDao(): VeiculoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
