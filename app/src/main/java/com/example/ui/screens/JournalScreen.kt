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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ConsultationSession
import com.example.ui.components.AtmosphericBackgroundGlow
import com.example.ui.theme.Charcoal
import com.example.ui.theme.IvorySurface
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.WarmSand
import com.example.ui.theme.WarmSlate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JournalScreen(
    sessions: List<ConsultationSession>,
    onDeleteSession: (ConsultationSession) -> Unit,
    onStartNewSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredSessions = remember(sessions, searchQuery) {
        if (searchQuery.isBlank()) sessions
        else sessions.filter {
            it.clientName.contains(searchQuery, ignoreCase = true) ||
                    it.stylistName.contains(searchQuery, ignoreCase = true) ||
                    it.selectedVariation.contains(searchQuery, ignoreCase = true)
        }
    }

    val dateFormatter = remember { SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault()) }

    Box(modifier = modifier.fillMaxSize()) {
        AtmosphericBackgroundGlow()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header
            Text(
                text = "Consultation Journal",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = Charcoal,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "Archived client previews, adjustments & haircut match logs.",
                fontSize = 14.sp,
                color = WarmSlate,
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by client, stylist, or style...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = WarmSlate.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                },
                singleLine = true,
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
                    .testTag("journal_search_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (filteredSessions.isEmpty()) {
                // Empty State
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(IvorySurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HistoryEdu,
                            contentDescription = null,
                            tint = KimeraCoral,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (searchQuery.isNotBlank()) "No Matching Consultations" else "No Consultations Yet",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Charcoal
                    )

                    Text(
                        text = if (searchQuery.isNotBlank()) "Try another client name or keyword." else "Completed consultations with match ratings will be safely archived here.",
                        fontSize = 13.sp,
                        color = WarmSlate,
                        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                    )

                    Button(
                        onClick = onStartNewSession,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KimeraCoral,
                            contentColor = RenderWhite
                        ),
                        modifier = Modifier.testTag("journal_new_session_button")
                    ) {
                        Text("Start Chair Consultation", fontWeight = FontWeight.SemiBold)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredSessions, key = { it.id }) { session ->
                        JournalSessionCard(
                            session = session,
                            dateStr = dateFormatter.format(Date(session.timestamp)),
                            onDelete = { onDeleteSession(session) }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun JournalSessionCard(
    session: ConsultationSession,
    dateStr: String,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(IvorySurface)
            .border(1.dp, WarmSand.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
            .padding(14.dp)
            .testTag("journal_card_${session.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = session.clientName,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Charcoal
                )
                Text(
                    text = "$dateStr • ${session.stylistName}",
                    fontSize = 11.sp,
                    color = WarmSlate
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Delete",
                    tint = WarmSlate.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = session.selectedVariation,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Charcoal
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SoftPeach.copy(alpha = 0.5f))
                    .border(1.dp, KimeraCoral.copy(alpha = 0.4f), CircleShape)
                    .padding(horizontal = 9.dp, vertical = 3.dp)
            ) {
                Text(
                    text = session.matchOutcome,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = KimeraCoral
                )
            }
        }

        if (session.stylistAdjustments.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Adjustments: ${session.stylistAdjustments}",
                fontSize = 11.sp,
                color = WarmSlate,
                maxLines = 2
            )
        }

        if (session.stylistNotes.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Note: \"${session.stylistNotes}\"",
                fontSize = 11.sp,
                color = Charcoal.copy(alpha = 0.8f),
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}
