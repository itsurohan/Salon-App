package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.ui.model.ConsultationStep
import com.example.ui.model.MatchOutcome
import com.example.ui.viewmodel.ConsultationViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ConsultationFlowTest {

    @Test
    fun `verify full consultation workflow step transitions`() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = ConsultationViewModel(app)

        // 1. Initial State
        assertEquals(ConsultationStep.SESSION_START, viewModel.uiState.value.currentStep)
        assertEquals("Rohan V.", viewModel.uiState.value.clientName)

        // 2. Start Session -> Consent
        viewModel.startSession()
        assertEquals(ConsultationStep.CONSENT, viewModel.uiState.value.currentStep)

        // 3. Confirm Consent -> Reference Capture
        viewModel.updatePhotoConsent(true)
        viewModel.updateRetentionOptIn(true)
        viewModel.confirmConsentAndProceed()
        assertEquals(ConsultationStep.REFERENCE_CAPTURE, viewModel.uiState.value.currentStep)

        // 4. Start Generation & Skip to results
        viewModel.startGeneration()
        assertEquals(ConsultationStep.GENERATING_PREVIEW, viewModel.uiState.value.currentStep)
        viewModel.skipGenerationToResults()
        assertEquals(ConsultationStep.RENDER_RESULTS, viewModel.uiState.value.currentStep)

        // 5. Select Variation & Confirm
        val varB = viewModel.defaultVariations[1]
        viewModel.selectVariation(varB)
        assertEquals(varB.id, viewModel.uiState.value.selectedVariation.id)
        viewModel.confirmSelectedLook()
        assertEquals(ConsultationStep.SELECTION_DISCUSSION, viewModel.uiState.value.currentStep)

        // 6. Ready to cut -> Outcome Log
        viewModel.readyToCut()
        assertEquals(ConsultationStep.OUTCOME_LOG, viewModel.uiState.value.currentStep)

        // 7. Verify match outcome
        viewModel.setMatchOutcome(MatchOutcome.PERFECT)
        assertEquals(MatchOutcome.PERFECT, viewModel.uiState.value.matchOutcome)

        // 8. Complete consultation -> Session Complete
        viewModel.completeConsultation()
        // Wait or check state transition
        assertEquals(ConsultationStep.SESSION_COMPLETE, viewModel.uiState.value.currentStep)
    }
}
