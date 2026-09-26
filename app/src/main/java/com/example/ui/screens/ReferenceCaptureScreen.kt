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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.R
import com.example.ui.components.AtmosphericBackgroundGlow
import com.example.ui.components.ConsultationStepBar
import com.example.ui.model.HairstyleLook
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReferenceCaptureScreen(
    clientName: String,
    targetStyle: HairstyleLook,
    availableStyles: List<HairstyleLook>,
    onTargetStyleSelected: (HairstyleLook) -> Unit,
    onGeneratePreviews: () -> Unit,
    onRetakePhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showStylePickerSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Box(modifier = modifier.fillMaxSize()) {
        AtmosphericBackgroundGlow()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            // Step Progress
            ConsultationStepBar(currentStepIndex = 2)

            Spacer(modifier = Modifier.height(18.dp))

            // Title
            Text(
                text = "Take Photo & Pick Style",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = Charcoal,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "Snap a quick chair photo and choose your target look.",
                fontSize = 14.sp,
                color = WarmSlate,
                modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
            )

            // Card 1: Client Photo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(KimeraCoral)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Client Photo",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Charcoal
                        )
                    }
                    Text(
                        text = clientName,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = WarmSlate
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(RenderWhite)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.client_portrait),
                        contentDescription = "Client portrait",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Floating Retake Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                            .clip(CircleShape)
                            .background(RenderWhite.copy(alpha = 0.95f))
                            .shadow(3.dp, CircleShape)
                            .clickable { onRetakePhoto() }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .testTag("retake_photo_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Retake",
                            tint = KimeraCoral,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Retake",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Charcoal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Card 2: Target Style
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(KimeraCoral)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Target Style",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Charcoal
                        )
                    }
                    TextButton(
                        onClick = { showStylePickerSheet = true },
                        modifier = Modifier.testTag("change_target_style_button")
                    ) {
                        Text(
                            text = "Change",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = KimeraCoral
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(RenderWhite)
                        .clickable { showStylePickerSheet = true }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = targetStyle.drawableRes),
                        contentDescription = targetStyle.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = targetStyle.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = Charcoal
                        )
                        Text(
                            text = targetStyle.subtitle,
                            fontSize = 12.sp,
                            color = WarmSlate,
                            maxLines = 1
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = KimeraCoral,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Primary Action Button
            Button(
                onClick = onGeneratePreviews,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = KimeraCoral,
                    contentColor = RenderWhite
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("generate_previews_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "GENERATE 3 PREVIEWS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Duration notice
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = WarmSlate,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Takes under 60 seconds",
                    fontSize = 12.sp,
                    color = WarmSlate
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Bottom Sheet for style selection
        if (showStylePickerSheet) {
            ModalBottomSheet(
                onDismissRequest = { showStylePickerSheet = false },
                sheetState = sheetState,
                containerColor = IvorySurface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .padding(bottom = 24.dp)
                ) {
                    Text(
                        text = "Select Target Haircut Silhouette",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Charcoal
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    availableStyles.forEach { style ->
                        val isSelected = style.id == targetStyle.id
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) RenderWhite else IvorySurface)
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) KimeraCoral else WarmSand,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable {
                                    onTargetStyleSelected(style)
                                    showStylePickerSheet = false
                                }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = style.drawableRes),
                                contentDescription = style.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = style.name,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = Charcoal
                                )
                                Text(
                                    text = style.subtitle,
                                    fontSize = 11.sp,
                                    color = WarmSlate,
                                    maxLines = 1
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = KimeraCoral,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
