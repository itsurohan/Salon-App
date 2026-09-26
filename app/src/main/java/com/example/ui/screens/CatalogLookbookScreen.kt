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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AtmosphericBackgroundGlow
import com.example.ui.model.HairstyleLook
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate

@Composable
fun CatalogLookbookScreen(
    looks: List<HairstyleLook>,
    selectedLook: HairstyleLook,
    isStylingMode: Boolean,
    onSelectLook: (HairstyleLook) -> Unit,
    onStartConsultationWithLook: (HairstyleLook) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AtmosphericBackgroundGlow()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = if (isStylingMode) "Chair-Side Styling Canvas" else "Kimera Lookbook",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = Charcoal,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = if (isStylingMode) "Bespoke scissor taper depths, guard lengths & wax finishes." else "Curated modern silhouettes crafted for Bangalore hair textures.",
                fontSize = 14.sp,
                color = WarmSlate,
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(looks, key = { it.id }) { look ->
                    val isSelected = look.id == selectedLook.id
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(IvorySurface)
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) KimeraCoral else WarmSand,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(14.dp)
                            .testTag("catalog_card_${look.id}")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(RenderWhite)
                            ) {
                                Image(
                                    painter = painterResource(id = look.drawableRes),
                                    contentDescription = look.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = look.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Charcoal
                                    )
                                    if (look.badgeLabel != null) {
                                        Box(
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .background(SoftPeach.copy(alpha = 0.5f))
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = look.badgeLabel,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = KimeraCoral
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = look.subtitle,
                                    fontSize = 12.sp,
                                    color = WarmSlate,
                                    modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    look.tags.take(2).forEach { tag ->
                                        Box(
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .background(SurfaceContainer)
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = tag,
                                                fontSize = 9.sp,
                                                color = Charcoal
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Action button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    onSelectLook(look)
                                    onStartConsultationWithLook(look)
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) KimeraCoral else Charcoal,
                                    contentColor = RenderWhite
                                ),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Icon(
                                    imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isSelected) "Active for Consultation" else "Use for Consultation",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}
