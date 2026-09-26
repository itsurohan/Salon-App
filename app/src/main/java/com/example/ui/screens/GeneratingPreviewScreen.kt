package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@Composable
fun GeneratingPreviewScreen(
    statusPhrase: String,
    elapsedSeconds: Int,
    progress: Float,
    onSkipToResults: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orb_pulse")
    val orbScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbScale"
    )

    val mins = String.format("%02d", elapsedSeconds / 60)
    val secs = String.format("%02d", elapsedSeconds % 60)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Brand Wordmark
            Text(
                text = "kimera",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = (-0.5).sp,
                color = Charcoal
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Title
            Text(
                text = "Crafting Your Previews",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = Charcoal,
                textAlign = TextAlign.Center,
                letterSpacing = (-0.5).sp
            )

            Text(
                text = "Infusing natural lighting and fade depth tailored to your contours.",
                fontSize = 14.sp,
                color = WarmSlate,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Central Glowing Coral/Peach AI Breathing Orb Stage
            Box(
                modifier = Modifier
                    .size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer diffuse gradient orb
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .scale(orbScale)
                        .blur(48.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    SecondaryContainer.copy(alpha = 0.85f),
                                    KimeraCoral.copy(alpha = 0.65f),
                                    SoftPeach.copy(alpha = 0.4f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // Middle soft glowing aura
                Box(
                    modifier = Modifier
                        .size(175.dp)
                        .scale(orbScale * 0.96f)
                        .blur(24.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    SoftPeach,
                                    KimeraCoral.copy(alpha = 0.6f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // Core elevated glass disc
                Column(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(RenderWhite.copy(alpha = 0.88f))
                        .border(1.dp, WarmSand.copy(alpha = 0.7f), CircleShape)
                        .shadow(12.dp, CircleShape)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(IvorySurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = KimeraCoral,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Generating 3\nPreviews",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Charcoal,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "BESPOKE TEXTURES",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp,
                        color = WarmSlate
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Dynamic Step Indicator Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(IvorySurface)
                    .border(1.dp, WarmSand.copy(alpha = 0.5f), CircleShape)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(KimeraCoral)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = statusPhrase,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Charcoal,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Elapsed Timer badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(IvorySurface.copy(alpha = 0.7f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = KimeraCoral,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "$mins:$secs",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Charcoal
                )
                Text(
                    text = "  /  Under 60s",
                    fontSize = 11.sp,
                    color = WarmSlate
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(5.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = KimeraCoral,
                trackColor = OutlineVariant.copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Skip / Instant Preview button (for developer and fast salon chair access)
            Button(
                onClick = onSkipToResults,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = IvorySurface,
                    contentColor = Charcoal
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .height(42.dp)
                    .testTag("skip_generation_button")
            ) {
                Text(
                    text = "View Previews Now",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Charcoal
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Footer coordinates
            Text(
                text = "Bengaluru • Jayanagar 4th Block",
                fontSize = 11.sp,
                color = WarmSlate.copy(alpha = 0.7f)
            )
        }
    }
}
