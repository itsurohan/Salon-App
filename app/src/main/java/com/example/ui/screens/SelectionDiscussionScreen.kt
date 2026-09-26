package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.ui.model.StylistAdjustment
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PrimaryFixedDim
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectionDiscussionScreen(
    selectedVariation: HairstyleLook,
    adjustments: List<StylistAdjustment>,
    stylistNotes: String,
    onToggleAdjustment: (String) -> Unit,
    onAddAdjustment: (String) -> Unit,
    onStylistNotesChange: (String) -> Unit,
    onReadyToCut: () -> Unit,
    modifier: Modifier = Modifier
) {
    var newAdjustmentText by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

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
                text = "Stylist Alignment",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = Charcoal,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "Confirm final adjustments before the first cut.",
                fontSize = 14.sp,
                color = WarmSlate,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Hero Look Preview Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.6f), RoundedCornerShape(22.dp))
                    .padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.2f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(RenderWhite)
                        .shadow(2.dp, RoundedCornerShape(18.dp))
                ) {
                    Image(
                        painter = painterResource(id = selectedVariation.drawableRes),
                        contentDescription = selectedVariation.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = selectedVariation.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Charcoal
                        )
                        Text(
                            text = "Front Profile Reference",
                            fontSize = 12.sp,
                            color = WarmSlate
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SoftPeach.copy(alpha = 0.45f))
                            .border(1.dp, KimeraCoral.copy(alpha = 0.4f), CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = KimeraCoral,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Confirmed",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = KimeraCoral
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stylist Adjustments Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STYLIST ADJUSTMENTS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = WarmSlate
                    )
                    val appliedCount = adjustments.count { it.isApplied }
                    Text(
                        text = "$appliedCount applied",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = KimeraCoral
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    adjustments.forEach { adj ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (adj.isApplied) SoftPeach.copy(alpha = 0.4f) else RenderWhite)
                                .border(
                                    width = if (adj.isApplied) 1.5.dp else 1.dp,
                                    color = if (adj.isApplied) KimeraCoral else WarmSand,
                                    shape = CircleShape
                                )
                                .clickable { onToggleAdjustment(adj.id) }
                                .padding(horizontal = 12.dp, vertical = 7.dp)
                                .testTag("adjustment_chip_${adj.id}")
                        ) {
                            if (adj.isApplied) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = KimeraCoral,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                            Text(
                                text = adj.label,
                                fontSize = 13.sp,
                                fontWeight = if (adj.isApplied) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (adj.isApplied) Charcoal else WarmSlate
                            )
                        }
                    }

                    // Add Custom Adjustment Chip
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(RenderWhite)
                            .border(1.dp, WarmSand, CircleShape)
                            .clickable { showAddDialog = !showAddDialog }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add custom adjustment",
                            tint = KimeraCoral,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Add note",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Charcoal
                        )
                    }
                }

                if (showAddDialog) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = newAdjustmentText,
                            onValueChange = { newAdjustmentText = it },
                            placeholder = { Text("e.g. Taper 1 guard lower", fontSize = 12.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = RenderWhite,
                                unfocusedContainerColor = RenderWhite,
                                focusedBorderColor = KimeraCoral,
                                unfocusedBorderColor = WarmSand
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (newAdjustmentText.isNotBlank()) {
                                    onAddAdjustment(newAdjustmentText)
                                    newAdjustmentText = ""
                                    showAddDialog = false
                                }
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = KimeraCoral)
                        ) {
                            Text("Add")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Stylist Note Field
                Text(
                    text = "STYLIST NOTE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = WarmSlate,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                OutlinedTextField(
                    value = stylistNotes,
                    onValueChange = onStylistNotesChange,
                    placeholder = { Text("Add specific chair guidance...", fontSize = 13.sp) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = null,
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
                        .testTag("stylist_notes_input")
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Primary CTA: Ready to Cut
            Button(
                onClick = onReadyToCut,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Charcoal,
                    contentColor = RenderWhite
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("ready_to_cut_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCut,
                        contentDescription = null,
                        tint = KimeraCoral,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Ready to Cut",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        letterSpacing = 0.3.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
