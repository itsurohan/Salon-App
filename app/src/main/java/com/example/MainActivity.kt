package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.KimeraBottomNav
import com.example.ui.components.KimeraTopBar
import com.example.ui.model.ConsultationStep
import com.example.ui.model.NavigationTab
import com.example.ui.screens.CatalogLookbookScreen
import com.example.ui.screens.ConsentScreen
import com.example.ui.screens.GeneratingPreviewScreen
import com.example.ui.screens.JournalScreen
import com.example.ui.screens.OutcomeLogScreen
import com.example.ui.screens.ReferenceCaptureScreen
import com.example.ui.screens.RenderResultsScreen
import com.example.ui.screens.SelectionDiscussionScreen
import com.example.ui.screens.SessionCompleteScreen
import com.example.ui.screens.SessionStartScreen
import com.example.ui.theme.Charcoal
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RenderWhite
import com.example.ui.viewmodel.ConsultationViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                KimeraApp()
            }
        }
    }
}

@Composable
fun KimeraApp(
    viewModel: ConsultationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pastSessions by viewModel.pastSessions.collectAsStateWithLifecycle()

    // Auto-clear toast after 2.5s
    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            delay(2800)
            viewModel.clearToast()
        }
    }

    // Back handling
    BackHandler(
        enabled = uiState.selectedTab != NavigationTab.SESSION || uiState.currentStep != ConsultationStep.SESSION_START
    ) {
        if (uiState.selectedTab != NavigationTab.SESSION) {
            viewModel.selectTab(NavigationTab.SESSION)
        } else {
            viewModel.navigateBack()
        }
    }

    val showTopBar = uiState.currentStep != ConsultationStep.GENERATING_PREVIEW
    val showBottomBar = uiState.currentStep != ConsultationStep.GENERATING_PREVIEW &&
            uiState.currentStep != ConsultationStep.SESSION_COMPLETE

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                val stepTitle = when (uiState.selectedTab) {
                    NavigationTab.SESSION -> when (uiState.currentStep) {
                        ConsultationStep.SESSION_START -> null
                        ConsultationStep.CONSENT -> "Consent"
                        ConsultationStep.REFERENCE_CAPTURE -> "Capture"
                        ConsultationStep.GENERATING_PREVIEW -> null
                        ConsultationStep.RENDER_RESULTS -> "Previews"
                        ConsultationStep.SELECTION_DISCUSSION -> "Discussion"
                        ConsultationStep.OUTCOME_LOG -> "Outcome Log"
                        ConsultationStep.SESSION_COMPLETE -> null
                    }
                    NavigationTab.PREVIEW -> "Lookbook"
                    NavigationTab.STYLING -> "Styling"
                    NavigationTab.JOURNAL -> "Journal"
                }

                val stepBadge = when (uiState.selectedTab) {
                    NavigationTab.SESSION -> when (uiState.currentStep) {
                        ConsultationStep.CONSENT -> "Step 1 of 4"
                        ConsultationStep.REFERENCE_CAPTURE -> "Step 2 of 4"
                        ConsultationStep.RENDER_RESULTS -> "Step 3 of 4"
                        ConsultationStep.SELECTION_DISCUSSION -> "Step 4 of 4"
                        ConsultationStep.OUTCOME_LOG -> "Step 4 of 4"
                        else -> null
                    }
                    else -> null
                }

                val canGoBack = (uiState.selectedTab == NavigationTab.SESSION &&
                        uiState.currentStep != ConsultationStep.SESSION_START &&
                        uiState.currentStep != ConsultationStep.SESSION_COMPLETE) ||
                        uiState.selectedTab != NavigationTab.SESSION

                KimeraTopBar(
                    title = stepTitle,
                    stepBadge = stepBadge,
                    canNavigateBack = canGoBack,
                    onNavigateBack = {
                        if (uiState.selectedTab != NavigationTab.SESSION) {
                            viewModel.selectTab(NavigationTab.SESSION)
                        } else {
                            viewModel.navigateBack()
                        }
                    },
                    chairLabel = uiState.selectedStylist.chair
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                KimeraBottomNav(
                    selectedTab = uiState.selectedTab,
                    onTabSelected = { viewModel.selectTab(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.selectedTab) {
                NavigationTab.SESSION -> {
                    when (uiState.currentStep) {
                        ConsultationStep.SESSION_START -> {
                            SessionStartScreen(
                                clientName = uiState.clientName,
                                onClientNameChange = { viewModel.updateClientName(it) },
                                selectedStylist = uiState.selectedStylist,
                                availableStylists = viewModel.availableStylists,
                                onStylistSelected = { viewModel.selectStylist(it) },
                                onStartSession = { viewModel.startSession() }
                            )
                        }

                        ConsultationStep.CONSENT -> {
                            ConsentScreen(
                                photoConsent = uiState.photoConsent,
                                onPhotoConsentChange = { viewModel.updatePhotoConsent(it) },
                                retentionOptIn = uiState.retentionOptIn,
                                onRetentionOptInChange = { viewModel.updateRetentionOptIn(it) },
                                onConfirmAndContinue = { viewModel.confirmConsentAndProceed() },
                                onSkipToVerbal = { viewModel.skipAiPreview() }
                            )
                        }

                        ConsultationStep.REFERENCE_CAPTURE -> {
                            ReferenceCaptureScreen(
                                clientName = uiState.clientName,
                                targetStyle = uiState.targetStyle,
                                availableStyles = viewModel.defaultVariations,
                                onTargetStyleSelected = { viewModel.selectTargetStyle(it) },
                                onGeneratePreviews = { viewModel.startGeneration() },
                                onRetakePhoto = {
                                    viewModel.showToast("Chair camera focus adjusted")
                                }
                            )
                        }

                        ConsultationStep.GENERATING_PREVIEW -> {
                            GeneratingPreviewScreen(
                                statusPhrase = uiState.generationStatusPhrase,
                                elapsedSeconds = uiState.generationElapsedSeconds,
                                progress = uiState.generationProgress,
                                onSkipToResults = { viewModel.skipGenerationToResults() }
                            )
                        }

                        ConsultationStep.RENDER_RESULTS -> {
                            RenderResultsScreen(
                                variations = uiState.variations,
                                selectedVariation = uiState.selectedVariation,
                                onSelectVariation = { viewModel.selectVariation(it) },
                                onConfirmLook = { viewModel.confirmSelectedLook() },
                                onRegenerate = {
                                    viewModel.showToast("Refining taper with custom notes...")
                                    viewModel.startGeneration()
                                }
                            )
                        }

                        ConsultationStep.SELECTION_DISCUSSION -> {
                            SelectionDiscussionScreen(
                                selectedVariation = uiState.selectedVariation,
                                adjustments = uiState.adjustments,
                                stylistNotes = uiState.stylistNotes,
                                onToggleAdjustment = { viewModel.toggleAdjustment(it) },
                                onAddAdjustment = { viewModel.addCustomAdjustment(it) },
                                onStylistNotesChange = { viewModel.updateStylistNotes(it) },
                                onReadyToCut = { viewModel.readyToCut() }
                            )
                        }

                        ConsultationStep.OUTCOME_LOG -> {
                            OutcomeLogScreen(
                                clientName = uiState.clientName,
                                chosenLook = uiState.selectedVariation,
                                selectedOutcome = uiState.matchOutcome,
                                onOutcomeSelected = { viewModel.setMatchOutcome(it) },
                                outcomeNotes = uiState.outcomeNotes,
                                onOutcomeNotesChange = { viewModel.updateOutcomeNotes(it) },
                                onCompleteConsultation = { viewModel.completeConsultation() }
                            )
                        }

                        ConsultationStep.SESSION_COMPLETE -> {
                            SessionCompleteScreen(
                                clientName = uiState.clientName,
                                styleName = uiState.selectedVariation.name,
                                stylistName = uiState.selectedStylist.name,
                                matchOutcome = uiState.matchOutcome.label,
                                retentionOptIn = uiState.retentionOptIn,
                                onSendBlueprint = {
                                    viewModel.showToast("Blueprint dispatched to ${uiState.clientName} (+91 98860 •••••)")
                                },
                                onStartNextSession = { viewModel.startNextConsultation() }
                            )
                        }
                    }
                }

                NavigationTab.PREVIEW -> {
                    CatalogLookbookScreen(
                        looks = viewModel.defaultVariations,
                        selectedLook = uiState.targetStyle,
                        isStylingMode = false,
                        onSelectLook = { viewModel.selectTargetStyle(it) },
                        onStartConsultationWithLook = {
                            viewModel.selectTargetStyle(it)
                            viewModel.navigateToStep(ConsultationStep.REFERENCE_CAPTURE)
                        }
                    )
                }

                NavigationTab.STYLING -> {
                    CatalogLookbookScreen(
                        looks = viewModel.defaultVariations,
                        selectedLook = uiState.targetStyle,
                        isStylingMode = true,
                        onSelectLook = { viewModel.selectTargetStyle(it) },
                        onStartConsultationWithLook = {
                            viewModel.selectTargetStyle(it)
                            viewModel.navigateToStep(ConsultationStep.SELECTION_DISCUSSION)
                        }
                    )
                }

                NavigationTab.JOURNAL -> {
                    JournalScreen(
                        sessions = pastSessions,
                        onDeleteSession = { viewModel.deleteSession(it) },
                        onStartNewSession = { viewModel.startNextConsultation() }
                    )
                }
            }

            // Floating Toast notification
            AnimatedVisibility(
                visible = uiState.toastMessage != null,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                uiState.toastMessage?.let { msg ->
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Charcoal.copy(alpha = 0.95f))
                            .shadow(8.dp, CircleShape)
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .testTag("app_toast_message"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = KimeraCoral,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = msg,
                            color = RenderWhite,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
