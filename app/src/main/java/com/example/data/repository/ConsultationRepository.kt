package com.example.data.repository

import com.example.data.local.ConsultationDao
import com.example.data.local.ConsultationSession
import kotlinx.coroutines.flow.Flow

class ConsultationRepository(private val dao: ConsultationDao) {
    val allSessions: Flow<List<ConsultationSession>> = dao.getAllSessions()

    fun getSessionById(id: Long): Flow<ConsultationSession?> = dao.getSessionById(id)

    suspend fun saveSession(session: ConsultationSession): Long = dao.insertSession(session)

    suspend fun deleteSession(session: ConsultationSession) = dao.deleteSession(session)

    suspend fun deleteById(id: Long) = dao.deleteById(id)
}
