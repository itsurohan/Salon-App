package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consultation_sessions")
data class ConsultationSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clientName: String,
    val stylistName: String,
    val chairNumber: String = "Chair 03",
    val targetStyleName: String,
    val selectedVariation: String,
    val photoConsentGranted: Boolean,
    val retentionConsentGranted: Boolean,
    val stylistAdjustments: String,
    val stylistNotes: String,
    val matchOutcome: String, // "Matched Perfectly", "Close Match", "Did Not Match"
    val outcomeNotes: String,
    val timestamp: Long = System.currentTimeMillis()
)
