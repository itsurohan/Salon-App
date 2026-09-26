package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsultationDao {
    @Query("SELECT * FROM consultation_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<ConsultationSession>>

    @Query("SELECT * FROM consultation_sessions WHERE id = :id LIMIT 1")
    fun getSessionById(id: Long): Flow<ConsultationSession?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ConsultationSession): Long

    @Delete
    suspend fun deleteSession(session: ConsultationSession)

    @Query("DELETE FROM consultation_sessions WHERE id = :id")
    suspend fun deleteById(id: Long)
}
