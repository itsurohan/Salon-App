package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@Composable
fun AtmosphericBackgroundGlow(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        // Top right peach orb
        Box(
            modifier = Modifier
                .size(240.dp)
                .offset(x = 180.dp, y = (-40).dp)
                .blur(60.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            SoftPeach.copy(alpha = 0.5f),
                            KimeraCoral.copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        // Mid left soft coral orb
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = (-80).dp, y = 200.dp)
                .blur(50.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            KimeraCoral.copy(alpha = 0.25f),
                            SoftPeach.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}

@Composable
fun ConsultationStepBar(
    currentStepIndex: Int, // 1: Consent, 2: Capture, 3: Previews, 4: Cut & Check
    modifier: Modifier = Modifier
) {
    val steps = listOf("Consent", "Capture", "Previews", "Cut & Check")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(IvorySurface.copy(alpha = 0.85f))
            .padding(12.dp)
    ) {
        // Progress Bars Row
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 1..4) {
                val isCompleted = i < currentStepIndex
                val isCurrent = i == currentStepIndex
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            when {
                                isCompleted -> KimeraCoral
                                isCurrent -> KimeraCoral
                                else -> OutlineVariant.copy(alpha = 0.4f)
                            }
                        )
                )
                if (i < 4) {
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Step Labels Row
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            steps.forEachIndexed { index, stepName ->
                val stepNum = index + 1
                val isCompleted = stepNum < currentStepIndex
                val isCurrent = stepNum == currentStepIndex

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = KimeraCoral,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                    } else if (isCurrent) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(KimeraCoral)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                    }

                    Text(
                        text = "$stepNum $stepName",
                        fontSize = 10.sp,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                        color = when {
                            isCurrent -> KimeraCoral
                            isCompleted -> Charcoal
                            else -> WarmSlate.copy(alpha = 0.7f)
                        },
                        maxLines = 1
                    )
                }
            }
        }
    }
}
