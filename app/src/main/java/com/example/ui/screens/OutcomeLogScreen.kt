package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AtmosphericBackgroundGlow
import com.example.ui.components.ConsultationStepBar
import com.example.ui.model.HairstyleLook
import com.example.ui.model.MatchOutcome
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@Composable
fun OutcomeLogScreen(
    clientName: String,
    chosenLook: HairstyleLook,
    selectedOutcome: MatchOutcome,
    onOutcomeSelected: (MatchOutcome) -> Unit,
    outcomeNotes: String,
    onOutcomeNotesChange: (String) -> Unit,
    onCompleteConsultation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AtmosphericBackgroundGlow()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            // Step Progress
            ConsultationStepBar(currentStepIndex = 4)

            Spacer(modifier = Modifier.height(18.dp))

            // Title
            Text(
                text = "Verify Haircut Match",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = Charcoal,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "Compare the finished cut with $clientName's chosen preview.",
                fontSize = 14.sp,
                color = WarmSlate,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Side-by-Side Verification Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.6f), RoundedCornerShape(22.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Chosen Preview Card
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(RenderWhite)
                            .padding(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Image(
                                painter = painterResource(id = chosenLook.drawableRes),
                                contentDescription = "Chosen Preview",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = KimeraCoral,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Chosen Preview",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Charcoal
                            )
                        }
                    }

                    // Completed Cut Card
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(RenderWhite)
                            .padding(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(IvorySurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(SurfaceContainerHigh),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCut,
                                        contentDescription = null,
                                        tint = KimeraCoral,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Chair Station 03",
                                    fontSize = 10.sp,
                                    color = WarmSlate,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Finished Cut",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Charcoal
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Completed Cut",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Charcoal
                        )
                    }
                }

                // Center floating swap/sync pill
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(RenderWhite)
                        .shadow(4.dp, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SyncAlt,
                        contentDescription = "Compared",
                        tint = KimeraCoral,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Match Evaluation Options
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MatchOptionRow(
                    title = "Matched Perfectly",
                    icon = Icons.Default.Check,
                    sentimentIcon = Icons.Default.SentimentVerySatisfied,
                    isSelected = selectedOutcome == MatchOutcome.PERFECT,
                    onClick = { onOutcomeSelected(MatchOutcome.PERFECT) },
                    testTag = "outcome_perfect_button"
                )

                MatchOptionRow(
                    title = "Close Match",
                    icon = Icons.Default.Remove,
                    sentimentIcon = Icons.Default.SentimentSatisfied,
                    isSelected = selectedOutcome == MatchOutcome.CLOSE,
                    onClick = { onOutcomeSelected(MatchOutcome.CLOSE) },
                    testTag = "outcome_close_button"
                )

                MatchOptionRow(
                    title = "Did Not Match",
                    icon = Icons.Default.Close,
                    sentimentIcon = Icons.Default.SentimentDissatisfied,
                    isSelected = selectedOutcome == MatchOutcome.NO_MATCH,
                    onClick = { onOutcomeSelected(MatchOutcome.NO_MATCH) },
                    testTag = "outcome_nomatch_button"
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Stylist & Client Notes Input
            Text(
                text = "Stylist & Client Notes (Optional)",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Charcoal,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            OutlinedTextField(
                value = outcomeNotes,
                onValueChange = onOutcomeNotesChange,
                placeholder = { Text("Add any notes or client remarks...", fontSize = 13.sp) },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = RenderWhite,
                    unfocusedContainerColor = IvorySurface,
                    focusedBorderColor = KimeraCoral,
                    unfocusedBorderColor = WarmSand,
                    focusedTextColor = Charcoal,
                    unfocusedTextColor = Charcoal
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("outcome_notes_input")
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Complete Consultation Button
            Button(
                onClick = onCompleteConsultation,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = KimeraCoral,
                    contentColor = RenderWhite
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("complete_consultation_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Complete Consultation",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MatchOptionRow(
    title: String,
    icon: ImageVector,
    sentimentIcon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(if (isSelected) SoftPeach.copy(alpha = 0.5f) else IvorySurface)
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) KimeraCoral else WarmSand,
                shape = CircleShape
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag(testTag),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) KimeraCoral else IvorySurface.copy(alpha = 0.8f))
                    .border(1.dp, if (isSelected) KimeraCoral else WarmSand, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) RenderWhite else WarmSlate,
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = Charcoal
            )
        }

        Icon(
            imageVector = sentimentIcon,
            contentDescription = null,
            tint = if (isSelected) KimeraCoral else WarmSlate.copy(alpha = 0.7f),
            modifier = Modifier.size(20.dp)
        )
    }
}
