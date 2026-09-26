package com.example.ui.model

import androidx.annotation.DrawableRes
import com.example.R

enum class ConsultationStep {
    SESSION_START,
    CONSENT,
    REFERENCE_CAPTURE,
    GENERATING_PREVIEW,
    RENDER_RESULTS,
    SELECTION_DISCUSSION,
    OUTCOME_LOG,
    SESSION_COMPLETE
}

enum class NavigationTab {
    SESSION,
    PREVIEW,
    STYLING,
    JOURNAL
}

data class Stylist(
    val id: String,
    val name: String,
    val role: String = "Master Stylist",
    val chair: String = "Chair 03"
)

data class HairstyleLook(
    val id: String,
    val name: String,
    val subtitle: String,
    val tags: List<String>,
    val badgeLabel: String?,
    @DrawableRes val drawableRes: Int
)

data class StylistAdjustment(
    val id: String,
    val label: String,
    val isApplied: Boolean = true
)

enum class MatchOutcome(val label: String, val ratingDescription: String) {
    PERFECT("Matched Perfectly", "Exceptional finish alignment"),
    CLOSE("Close Match", "Minor nuance variation"),
    NO_MATCH("Did Not Match", "Required post-adjustment")
}
