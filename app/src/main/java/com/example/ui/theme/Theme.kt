package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SalonColorScheme = lightColorScheme(
    primary = KimeraCoral,
    onPrimary = OnPrimary,
    primaryContainer = KimeraCoral,
    onPrimaryContainer = Charcoal,
    secondary = DeepCoral,
    onSecondary = OnPrimary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = Charcoal,
    tertiary = TertiaryBrown,
    onTertiary = OnPrimary,
    tertiaryContainer = SoftPeach,
    onTertiaryContainer = Charcoal,
    background = WarmCream,
    onBackground = Charcoal,
    surface = WarmCream,
    onSurface = Charcoal,
    surfaceVariant = IvorySurface,
    onSurfaceVariant = WarmSlate,
    outline = WarmSand,
    outlineVariant = OutlineVariant
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SalonColorScheme,
        typography = Typography,
        content = content
    )
}
