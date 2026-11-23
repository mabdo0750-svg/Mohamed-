package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Immutable
data class DaraExtendedColors(
    val availableText: Color,
    val availableContainer: Color,
    val soldOutText: Color,
    val soldOutContainer: Color,
    val goldAccent: Color,
    val goldContainer: Color,
    val onGoldContainer: Color,
    val heroScrimStart: Color,
    val heroScrimEnd: Color
)

val LocalDaraColors = staticCompositionLocalOf {
    DaraExtendedColors(
        availableText = AvailableEmeraldLight,
        availableContainer = AvailableEmeraldBgLight,
        soldOutText = SoldOutSlateLight,
        soldOutContainer = SoldOutBgLight,
        goldAccent = DesertGold,
        goldContainer = DesertGoldContainer,
        onGoldContainer = OnDesertGoldContainer,
        heroScrimStart = Color(0xCC06272A),
        heroScrimEnd = Color(0x2606272A)
    )
}

private val LightColorScheme = lightColorScheme(
    primary = SuezTeal,
    onPrimary = Color.White,
    primaryContainer = SuezTealContainer,
    onPrimaryContainer = OnSuezTealContainer,
    secondary = TerracottaCopper,
    onSecondary = Color.White,
    secondaryContainer = TerracottaContainer,
    onSecondaryContainer = OnTerracottaContainer,
    tertiary = DesertGold,
    onTertiary = Color.White,
    tertiaryContainer = DesertGoldContainer,
    onTertiaryContainer = OnDesertGoldContainer,
    background = LimestoneBg,
    onBackground = SlateInk,
    surface = LimestoneSurface,
    onSurface = SlateInk,
    surfaceVariant = SoftSandVariant,
    onSurfaceVariant = SlateMuted,
    outline = WarmOutline,
    outlineVariant = WarmOutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkTealPrimary,
    onPrimary = SuezTealDark,
    primaryContainer = DarkTealContainer,
    onPrimaryContainer = Color(0xFFD0EBEC),
    secondary = DarkTerracotta,
    onSecondary = Color(0xFF3E1904),
    secondaryContainer = DarkTerracottaContainer,
    onSecondaryContainer = Color(0xFFFCE4D6),
    tertiary = DarkGold,
    onTertiary = Color(0xFF362406),
    tertiaryContainer = DarkGoldContainer,
    onTertiaryContainer = Color(0xFFF6E9CE),
    background = DarkSuezBg,
    onBackground = DarkSuezInk,
    surface = DarkSuezSurface,
    onSurface = DarkSuezInk,
    surfaceVariant = DarkSuezVariant,
    onSurfaceVariant = DarkSuezMuted,
    outline = Color(0xFF425054),
    outlineVariant = Color(0xFF283538)
)

private val DaraShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun DaraElSuezTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) {
        DaraExtendedColors(
            availableText = AvailableEmeraldDark,
            availableContainer = AvailableEmeraldBgDark,
            soldOutText = SoldOutSlateDark,
            soldOutContainer = SoldOutBgDark,
            goldAccent = DarkGold,
            goldContainer = DarkGoldContainer,
            onGoldContainer = Color(0xFFF6E9CE),
            heroScrimStart = Color(0xE0061B1D),
            heroScrimEnd = Color(0x33061B1D)
        )
    } else {
        DaraExtendedColors(
            availableText = AvailableEmeraldLight,
            availableContainer = AvailableEmeraldBgLight,
            soldOutText = SoldOutSlateLight,
            soldOutContainer = SoldOutBgLight,
            goldAccent = DesertGold,
            goldContainer = DesertGoldContainer,
            onGoldContainer = OnDesertGoldContainer,
            heroScrimStart = Color(0xD906272A),
            heroScrimEnd = Color(0x1F06272A)
        )
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl,
        LocalDaraColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = DaraTypography,
            shapes = DaraShapes,
            content = content
        )
    }
}
