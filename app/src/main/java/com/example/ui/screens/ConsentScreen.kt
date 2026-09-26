package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AtmosphericBackgroundGlow
import com.example.ui.components.ConsultationStepBar
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.PrimaryFixedDim
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@Composable
fun ConsentScreen(
    photoConsent: Boolean,
    onPhotoConsentChange: (Boolean) -> Unit,
    retentionOptIn: Boolean,
    onRetentionOptInChange: (Boolean) -> Unit,
    onConfirmAndContinue: () -> Unit,
    onSkipToVerbal: () -> Unit,
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
            ConsultationStepBar(currentStepIndex = 1)

            Spacer(modifier = Modifier.height(18.dp))

            // Section Header
            Text(
                text = "PRIVACY & PERMISSIONS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = KimeraCoral
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Photo & Privacy",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = Charcoal,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "We need a quick photo to adapt styles to your face shape.",
                fontSize = 14.sp,
                color = WarmSlate,
                lineHeight = 20.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            // Card 1: Mandatory Photo Consent
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(RenderWhite)
                    .border(
                        width = if (photoConsent) 1.5.dp else 1.dp,
                        color = if (photoConsent) KimeraCoral.copy(alpha = 0.5f) else WarmSand,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .clickable { onPhotoConsentChange(!photoConsent) }
                    .padding(16.dp)
                    .testTag("mandatory_consent_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Camera & AI Preview",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Charcoal
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(PrimaryFixedDim.copy(alpha = 0.5f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Required",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Charcoal
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Used only during this consultation to preview hairstyles.",
                            fontSize = 13.sp,
                            color = WarmSlate,
                            lineHeight = 18.sp
                        )
                    }

                    Switch(
                        checked = photoConsent,
                        onCheckedChange = onPhotoConsentChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = RenderWhite,
                            checkedTrackColor = KimeraCoral,
                            uncheckedThumbColor = RenderWhite,
                            uncheckedTrackColor = OutlineVariant
                        ),
                        modifier = Modifier.testTag("mandatory_consent_switch")
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Card 2: Retention Opt-in
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(RenderWhite)
                    .border(
                        width = if (retentionOptIn) 1.5.dp else 1.dp,
                        color = if (retentionOptIn) KimeraCoral.copy(alpha = 0.5f) else WarmSand,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .clickable { onRetentionOptInChange(!retentionOptIn) }
                    .padding(16.dp)
                    .testTag("retention_consent_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Save for Next Visit",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Charcoal
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(IvorySurface)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Optional",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WarmSlate
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Keep your adapted blueprint on file for future touch-ups.",
                            fontSize = 13.sp,
                            color = WarmSlate,
                            lineHeight = 18.sp
                        )
                    }

                    Switch(
                        checked = retentionOptIn,
                        onCheckedChange = onRetentionOptInChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = RenderWhite,
                            checkedTrackColor = KimeraCoral,
                            uncheckedThumbColor = RenderWhite,
                            uncheckedTrackColor = OutlineVariant
                        ),
                        modifier = Modifier.testTag("retention_consent_switch")
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Primary CTA
            Button(
                onClick = onConfirmAndContinue,
                enabled = photoConsent,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = KimeraCoral,
                    contentColor = RenderWhite,
                    disabledContainerColor = OutlineVariant.copy(alpha = 0.5f),
                    disabledContentColor = RenderWhite.copy(alpha = 0.6f)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("confirm_consent_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (photoConsent) "Continue to Photo" else "Consent Required",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = if (photoConsent) Icons.AutoMirrored.Filled.ArrowForward else Icons.Outlined.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Secondary Skip CTA
            TextButton(
                onClick = onSkipToVerbal,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("skip_consent_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = null,
                        tint = WarmSlate,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Skip AI Preview (Verbal Consultation)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarmSlate
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
