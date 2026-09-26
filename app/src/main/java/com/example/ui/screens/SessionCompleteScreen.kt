package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SendToMobile
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AtmosphericBackgroundGlow
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@Composable
fun SessionCompleteScreen(
    clientName: String,
    styleName: String,
    stylistName: String,
    matchOutcome: String,
    retentionOptIn: Boolean,
    onSendBlueprint: () -> Unit,
    onStartNextSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AtmosphericBackgroundGlow()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(IvorySurface)
                    .border(1.dp, WarmSand, CircleShape)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = KimeraCoral,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SESSION COMPLETE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = Charcoal
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Header Section
            Text(
                text = "Session Complete",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = Charcoal,
                textAlign = TextAlign.Center,
                letterSpacing = (-0.5).sp
            )

            Text(
                text = "$clientName • $styleName",
                fontSize = 15.sp,
                color = WarmSlate,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 22.dp)
            )

            // Primary Consultation Summary Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.6f), RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CONSULTATION BLUEPRINT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = Charcoal
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = KimeraCoral,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Archived",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = KimeraCoral
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                val details = listOf(
                    "Client" to clientName,
                    "Hairstyle" to styleName,
                    "Stylist" to stylistName,
                    "Match Rating" to matchOutcome,
                    "Data Retention" to if (retentionOptIn) "Stored for next visit" else "Single-session clearance"
                )

                details.forEach { (label, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            color = WarmSlate
                        )
                        Text(
                            text = value,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Charcoal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Privacy & Retention Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(RenderWhite)
                    .border(1.dp, WarmSand.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = KimeraCoral,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = if (retentionOptIn) "Profile saved securely" else "Photo cleared per policy",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Charcoal
                    )
                    Text(
                        text = if (retentionOptIn) "Ready for $clientName's next consultation" else "Chair image deleted after cut confirmation",
                        fontSize = 11.sp,
                        color = WarmSlate
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Actions
            Button(
                onClick = onSendBlueprint,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = KimeraCoral,
                    contentColor = RenderWhite
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("send_blueprint_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SendToMobile,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Send Blueprint to Client",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onStartNextSession,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = RenderWhite,
                    contentColor = Charcoal
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("start_next_consultation_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        tint = KimeraCoral,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Start Next Consultation",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Charcoal
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Studio Coordinates Footer
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "kimera",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    letterSpacing = (-0.5).sp,
                    color = Charcoal
                )
                Text(
                    text = "10TH MAIN, 4TH BLOCK • JAYANAGAR, BENGALURU",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.8.sp,
                    color = WarmSlate.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
