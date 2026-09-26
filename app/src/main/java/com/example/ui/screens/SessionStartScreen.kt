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
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.AtmosphericBackgroundGlow
import com.example.ui.model.Stylist
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@Composable
fun SessionStartScreen(
    clientName: String,
    onClientNameChange: (String) -> Unit,
    selectedStylist: Stylist,
    availableStylists: List<Stylist>,
    onStylistSelected: (Stylist) -> Unit,
    onStartSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AtmosphericBackgroundGlow()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header Typography
            Text(
                text = "New Consultation",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = Charcoal,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "Start a chair-side hairstyle preview",
                fontSize = 14.sp,
                color = WarmSlate,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Salon Suite Banner Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.salon_suite),
                    contentDescription = "Salon Suite",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Bottom gradient scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Charcoal.copy(alpha = 0.75f)),
                                startY = 120f
                            )
                        )
                )
                // Chair info tag
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.EventSeat,
                        contentDescription = null,
                        tint = SoftPeach,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Suite North • Chair 03 Ready",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = RenderWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Client Name Field
            Text(
                text = "Client Name",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Charcoal,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = clientName,
                onValueChange = onClientNameChange,
                placeholder = { Text("e.g. Rohan V.", color = WarmSlate.copy(alpha = 0.6f)) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Client",
                        tint = WarmSlate.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = RenderWhite,
                    unfocusedContainerColor = RenderWhite,
                    focusedBorderColor = KimeraCoral,
                    unfocusedBorderColor = WarmSand,
                    focusedTextColor = Charcoal,
                    unfocusedTextColor = Charcoal
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_name_input")
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Stylist Selector
            Text(
                text = "Stylist",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Charcoal,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                availableStylists.forEach { stylist ->
                    val isSelected = stylist.id == selectedStylist.id
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isSelected) SoftPeach.copy(alpha = 0.4f) else IvorySurface)
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) KimeraCoral else WarmSand,
                                shape = CircleShape
                            )
                            .clickable { onStylistSelected(stylist) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("stylist_chip_${stylist.id}")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) KimeraCoral else Color.Transparent)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = stylist.name,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isSelected) Charcoal else WarmSlate
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Consultation Process Recap
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CONSULTATION PROCESS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = Charcoal
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SoftPeach.copy(alpha = 0.45f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "4 Steps",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = KimeraCoral
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val processSteps = listOf(
                        "01" to "Consent",
                        "02" to "Capture",
                        "03" to "Previews",
                        "04" to "Cut & Check"
                    )

                    processSteps.forEachIndexed { idx, (num, label) ->
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(if (idx == 0) KimeraCoral else OutlineVariant.copy(alpha = 0.5f))
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = num,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (idx == 0) KimeraCoral else WarmSlate.copy(alpha = 0.7f)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    fontWeight = if (idx == 0) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (idx == 0) Charcoal else WarmSlate,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Start Session Button
            Button(
                onClick = onStartSession,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = KimeraCoral,
                    contentColor = RenderWhite
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("start_session_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Start Session",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.2.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
