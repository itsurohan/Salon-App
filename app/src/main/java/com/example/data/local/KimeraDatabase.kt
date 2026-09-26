package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ConsultationSession::class],
    version = 1,
    exportSchema = false
)
abstract class KimeraDatabase : RoomDatabase() {
    abstract fun consultationDao(): ConsultationDao

    companion object {
        @Volatile
        private var INSTANCE: KimeraDatabase? = null

        fun getInstance(context: Context): KimeraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KimeraDatabase::class.java,
                    "kimera_salon.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
