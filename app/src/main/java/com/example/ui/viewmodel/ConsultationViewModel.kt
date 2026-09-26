package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.local.ConsultationSession
import com.example.data.local.KimeraDatabase
import com.example.data.repository.ConsultationRepository
import com.example.ui.model.ConsultationStep
import com.example.ui.model.HairstyleLook
import com.example.ui.model.MatchOutcome
import com.example.ui.model.NavigationTab
import com.example.ui.model.Stylist
import com.example.ui.model.StylistAdjustment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConsultationUiState(
    val currentStep: ConsultationStep = ConsultationStep.SESSION_START,
    val selectedTab: NavigationTab = NavigationTab.SESSION,
    val clientName: String = "Rohan V.",
    val selectedStylist: Stylist = Stylist("vikram", "Vikram", "Master Stylist", "Chair 03"),
    val photoConsent: Boolean = true,
    val retentionOptIn: Boolean = false,
    val targetStyle: HairstyleLook,
    val variations: List<HairstyleLook>,
    val selectedVariation: HairstyleLook,
    val isGenerating: Boolean = false,
    val generationProgress: Float = 0f,
    val generationElapsedSeconds: Int = 0,
    val generationStatusPhrase: String = "Adapting fade & texture to face structure...",
    val adjustments: List<StylistAdjustment> = listOf(
        StylistAdjustment("adj_1", "Slightly longer fringe", true),
        StylistAdjustment("adj_2", "Low skin fade (0.5)", true),
        StylistAdjustment("adj_3", "Natural matte texture", true)
    ),
    val stylistNotes: String = "Fade 1cm lower on right temple",
    val matchOutcome: MatchOutcome = MatchOutcome.PERFECT,
    val outcomeNotes: String = "Client loved the texture adaptation and lower fade.",
    val lastSavedSessionId: Long? = null,
    val toastMessage: String? = null
)

class ConsultationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ConsultationRepository

    val availableStylists = listOf(
        Stylist("vikram", "Vikram", "Master Stylist", "Chair 03"),
        Stylist("anand", "Anand", "Senior Stylist", "Chair 02"),
        Stylist("sameer", "Sameer", "Lead Barber", "Chair 01")
    )

    val defaultVariations = listOf(
        HairstyleLook(
            id = "A",
            name = "Textured French Crop",
            subtitle = "Clean high-taper fade with natural cropped texture",
            tags = listOf("Low Maintenance", "Clean Fade", "Daily Friendly"),
            badgeLabel = "Top Stylist Match",
            drawableRes = R.drawable.crop_look_a
        ),
        HairstyleLook(
            id = "B",
            name = "Relaxed Pompadour",
            subtitle = "Natural flow with soft volume and tailored taper",
            tags = listOf("Medium Hold", "Side Flow", "Executive"),
            badgeLabel = "Executive",
            drawableRes = R.drawable.crop_look_b
        ),
        HairstyleLook(
            id = "C",
            name = "Modern Soft Mullet",
            subtitle = "Textured layers with subtle temple fade",
            tags = listOf("Textured Finish", "Temple Fade", "Editorial"),
            badgeLabel = "Editorial",
            drawableRes = R.drawable.crop_look_c
        )
    )

    private val _uiState = MutableStateFlow(
        ConsultationUiState(
            targetStyle = defaultVariations[0],
            variations = defaultVariations,
            selectedVariation = defaultVariations[0]
        )
    )
    val uiState: StateFlow<ConsultationUiState> = _uiState.asStateFlow()

    init {
        val database = KimeraDatabase.getInstance(application)
        repository = ConsultationRepository(database.consultationDao())
    }

    val pastSessions: StateFlow<List<ConsultationSession>> = repository.allSessions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private var generationJob: Job? = null

    fun updateClientName(name: String) {
        _uiState.update { it.copy(clientName = name) }
    }

    fun selectStylist(stylist: Stylist) {
        _uiState.update { it.copy(selectedStylist = stylist) }
    }

    fun startSession() {
        _uiState.update { it.copy(currentStep = ConsultationStep.CONSENT) }
    }

    fun updatePhotoConsent(granted: Boolean) {
        _uiState.update { it.copy(photoConsent = granted) }
    }

    fun updateRetentionOptIn(optIn: Boolean) {
        _uiState.update { it.copy(retentionOptIn = optIn) }
    }

    fun confirmConsentAndProceed() {
        if (_uiState.value.photoConsent) {
            _uiState.update { it.copy(currentStep = ConsultationStep.REFERENCE_CAPTURE) }
        }
    }

    fun skipAiPreview() {
        // Verbal consultation path per prompt
        showToast("Switching to standard mirror consultation")
        _uiState.update { it.copy(currentStep = ConsultationStep.SESSION_START) }
    }

    fun selectTargetStyle(style: HairstyleLook) {
        _uiState.update { it.copy(targetStyle = style) }
    }

    fun startGeneration() {
        _uiState.update {
            it.copy(
                currentStep = ConsultationStep.GENERATING_PREVIEW,
                isGenerating = true,
                generationProgress = 0.05f,
                generationElapsedSeconds = 0,
                generationStatusPhrase = "Harmonizing reference taper to Rohan's natural profile..."
            )
        }

        generationJob?.cancel()
        generationJob = viewModelScope.launch {
            val phrases = listOf(
                "Harmonizing reference taper to Rohan's natural profile...",
                "Balancing crown volume against cowlick growth...",
                "Calibrating scissor taper depths...",
                "Harmonizing light with salon mirror ambience...",
                "Synthesizing Look 02: Modern Taper...",
                "Refining styling wax & finish simulation..."
            )

            var seconds = 0
            var progress = 0.05f
            var phraseIndex = 0

            while (progress < 1.0f) {
                delay(700)
                seconds += 1
                progress += 0.15f
                phraseIndex = (phraseIndex + 1) % phrases.size

                _uiState.update {
                    it.copy(
                        generationElapsedSeconds = seconds,
                        generationProgress = progress.coerceAtMost(1f),
                        generationStatusPhrase = phrases[phraseIndex]
                    )
                }
            }

            delay(400)
            _uiState.update {
                it.copy(
                    isGenerating = false,
                    currentStep = ConsultationStep.RENDER_RESULTS
                )
            }
        }
    }

    fun skipGenerationToResults() {
        generationJob?.cancel()
        _uiState.update {
            it.copy(
                isGenerating = false,
                currentStep = ConsultationStep.RENDER_RESULTS
            )
        }
    }

    fun selectVariation(variation: HairstyleLook) {
        _uiState.update { it.copy(selectedVariation = variation) }
    }

    fun confirmSelectedLook() {
        _uiState.update { it.copy(currentStep = ConsultationStep.SELECTION_DISCUSSION) }
    }

    fun toggleAdjustment(id: String) {
        _uiState.update { state ->
            val updated = state.adjustments.map {
                if (it.id == id) it.copy(isApplied = !it.isApplied) else it
            }
            state.copy(adjustments = updated)
        }
    }

    fun addCustomAdjustment(text: String) {
        if (text.isBlank()) return
        val newAdj = StylistAdjustment("custom_${System.currentTimeMillis()}", text.trim(), true)
        _uiState.update { it.copy(adjustments = it.adjustments + newAdj) }
    }

    fun updateStylistNotes(notes: String) {
        _uiState.update { it.copy(stylistNotes = notes) }
    }

    fun readyToCut() {
        _uiState.update { it.copy(currentStep = ConsultationStep.OUTCOME_LOG) }
    }

    fun setMatchOutcome(outcome: MatchOutcome) {
        _uiState.update { it.copy(matchOutcome = outcome) }
    }

    fun updateOutcomeNotes(notes: String) {
        _uiState.update { it.copy(outcomeNotes = notes) }
    }

    fun completeConsultation() {
        val state = _uiState.value
        val appliedAdjustments = state.adjustments
            .filter { it.isApplied }
            .joinToString(separator = ", ") { it.label }

        val session = ConsultationSession(
            clientName = state.clientName.ifBlank { "Client" },
            stylistName = state.selectedStylist.name,
            chairNumber = state.selectedStylist.chair,
            targetStyleName = state.targetStyle.name,
            selectedVariation = state.selectedVariation.name,
            photoConsentGranted = state.photoConsent,
            retentionConsentGranted = state.retentionOptIn,
            stylistAdjustments = appliedAdjustments,
            stylistNotes = state.stylistNotes,
            matchOutcome = state.matchOutcome.label,
            outcomeNotes = state.outcomeNotes,
            timestamp = System.currentTimeMillis()
        )

        _uiState.update {
            it.copy(currentStep = ConsultationStep.SESSION_COMPLETE)
        }

        viewModelScope.launch {
            val id = repository.saveSession(session)
            _uiState.update {
                it.copy(lastSavedSessionId = id)
            }
            showToast("Consultation archived to workstation")
        }
    }

    fun startNextConsultation() {
        _uiState.update {
            ConsultationUiState(
                targetStyle = defaultVariations[0],
                variations = defaultVariations,
                selectedVariation = defaultVariations[0],
                currentStep = ConsultationStep.SESSION_START,
                selectedTab = NavigationTab.SESSION,
                clientName = "Next Client"
            )
        }
        showToast("Resetting canvas for next walk-in")
    }

    fun navigateBack() {
        val prevStep = when (_uiState.value.currentStep) {
            ConsultationStep.SESSION_START -> null
            ConsultationStep.CONSENT -> ConsultationStep.SESSION_START
            ConsultationStep.REFERENCE_CAPTURE -> ConsultationStep.CONSENT
            ConsultationStep.GENERATING_PREVIEW -> ConsultationStep.REFERENCE_CAPTURE
            ConsultationStep.RENDER_RESULTS -> ConsultationStep.REFERENCE_CAPTURE
            ConsultationStep.SELECTION_DISCUSSION -> ConsultationStep.RENDER_RESULTS
            ConsultationStep.OUTCOME_LOG -> ConsultationStep.SELECTION_DISCUSSION
            ConsultationStep.SESSION_COMPLETE -> ConsultationStep.SESSION_START
        }
        if (prevStep != null) {
            _uiState.update { it.copy(currentStep = prevStep) }
        }
    }

    fun selectTab(tab: NavigationTab) {
        _uiState.update { it.copy(selectedTab = tab) }
        if (tab == NavigationTab.SESSION && _uiState.value.currentStep == ConsultationStep.SESSION_COMPLETE) {
            // keep session complete or navigate
        }
    }

    fun navigateToStep(step: ConsultationStep) {
        _uiState.update { it.copy(currentStep = step, selectedTab = NavigationTab.SESSION) }
    }

    fun deleteSession(session: ConsultationSession) {
        viewModelScope.launch {
            repository.deleteSession(session)
            showToast("Session removed from journal")
        }
    }

    fun showToast(msg: String) {
        _uiState.update { it.copy(toastMessage = msg) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
