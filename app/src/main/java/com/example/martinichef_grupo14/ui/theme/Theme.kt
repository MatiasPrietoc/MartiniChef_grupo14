package com.example.martinichef_grupo14.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF426B37),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD8EACD),
    onPrimaryContainer = Color(0xFF153609),

    secondary = Color(0xFFA7462F),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDAD0),
    onSecondaryContainer = Color(0xFF3B0C02),

    tertiary = Color(0xFF7D5700),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDEA3),
    onTertiaryContainer = Color(0xFF281900),

    background = Color(0xFFFFF8F0),
    onBackground = Color(0xFF28251F),

    surface = Color(0xFFFFF8F0),
    onSurface = Color(0xFF28251F),
    surfaceVariant = Color(0xFFF0E6D5),
    onSurfaceVariant = Color(0xFF514A3F),

    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFF3E5),
    surfaceContainer = Color(0xFFF8EEDC),
    surfaceContainerHigh = Color(0xFFF0E6D5),
    surfaceContainerHighest = Color(0xFFE9DFCE),

    outline = Color(0xFF787164),
    outlineVariant = Color(0xFFD0C6B6),
    surfaceTint = Color(0xFF426B37),

    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFABD18F),
    onPrimary = Color(0xFF18380C),
    primaryContainer = Color(0xFF2C5120),
    onPrimaryContainer = Color(0xFFD8EACD),

    secondary = Color(0xFFFFB59D),
    onSecondary = Color(0xFF55200F),
    secondaryContainer = Color(0xFF79321D),
    onSecondaryContainer = Color(0xFFFFDAD0),

    tertiary = Color(0xFFEBC16D),
    onTertiary = Color(0xFF422D00),
    tertiaryContainer = Color(0xFF604200),
    onTertiaryContainer = Color(0xFFFFDEA3),

    background = Color(0xFF181C16),
    onBackground = Color(0xFFE6E9DC),

    surface = Color(0xFF181C16),
    onSurface = Color(0xFFE6E9DC),
    surfaceVariant = Color(0xFF394333),
    onSurfaceVariant = Color(0xFFC6D0BB),

    surfaceContainerLowest = Color(0xFF11150F),
    surfaceContainerLow = Color(0xFF20251D),
    surfaceContainer = Color(0xFF252A22),
    surfaceContainerHigh = Color(0xFF30362B),
    surfaceContainerHighest = Color(0xFF3A4035),

    outline = Color(0xFF909A85),
    outlineVariant = Color(0xFF434D3C),
    surfaceTint = Color(0xFFABD18F),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun MartiniChef_grupo14Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}