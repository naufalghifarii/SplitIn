package com.example.fairsplit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fairsplit.R


// Colors for Light and Dark Themes
val LightTextColor = Color(0xFF1C1B1F)  // Dark gray for text on light background
val DarkTextColor = Color(0xFFF5F5F5)  // White for text on dark background
val LightSecondaryTextColor = Color(0xFF616161)  // Medium gray for secondary text on light theme
val DarkSecondaryTextColor = Color(0xFFB0B0B0)  // Light gray for secondary text on dark theme
val HintTextColor = Color(0xFF9E9E9E)  // Lighter gray for hint/placeholder text
val ErrorTextColor = Color(0xFFB00020)  // Red color for error messages
val SuccessTextColor = Color(0xFF388E3C)  // Green color for success messages
val LinkTextColor = Color(0xFF2196F3)  // Blue color for links



//val LightPrimaryColor = Color(0xFF4D6A6D)      // Deep maroon
//val LightSecondaryColor = Color(0xFFFFA552)    // Warm caramel
//
//// Subtly tinted near-whites — keep things clean but not sterile
//val LightBackgroundColor = Color(0xFFF5F5F5)   // Near-white with a soft neutral beige/pink undertone
//val LightSurfaceColor = Color(0xFFFFFFFF)      // Slight maroonish tint
//
//// Text/icon contrast
//val LightOnPrimaryColor = Color.White
//val LightOnSecondaryColor = Color(0xFF2B1A12)  // Better contrast on caramel
//val LightOnBackgroundColor = Color(0xFF1F1F1F)
//val LightOnSurfaceColor = Color(0xFF2A2A2A)
//
//// Dark Color scheme
//val DarkPrimaryColor = Color(0xFF4D6A6D)       // Muted light version of maroon
//val DarkSecondaryColor = Color(0xFFFFB866)     // Lighter warm orange
//val DarkBackgroundColor = Color(0xFF121212)    // Standard deep dark
//val DarkSurfaceColor = Color(0xFF1E1E1E)       // Slightly lighter surface
//val DarkOnPrimaryColor = Color.Black           // Text on light maroon
//val DarkOnSecondaryColor = Color.Black         // Text on light orange
//val DarkOnBackgroundColor = Color(0xFFEDEDED)  // Light gray text
//val DarkOnSurfaceColor = Color(0xFFDADADA)     // Soft light gray text


//private val DarkColorScheme = darkColorScheme(
//    primary = DarkPrimaryColor,
//    secondary = DarkSecondaryColor,
//    background = DarkBackgroundColor,
//    surface = DarkSurfaceColor,
//    onPrimary = DarkOnPrimaryColor,
//    onSecondary = DarkOnSecondaryColor,
//    onBackground = DarkOnBackgroundColor,
//    onSurface = DarkOnSurfaceColor,
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = LightPrimaryColor,
//    secondary = LightSecondaryColor,
//    background = LightBackgroundColor,
//    surface = LightSurfaceColor,
//    onPrimary = LightOnPrimaryColor,
//    onSecondary = LightOnSecondaryColor,
//    onBackground = LightOnBackgroundColor,
//    onSurface = LightOnSurfaceColor,
//
////    onSurfaceVariant = LightSecondaryTextColor
//)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4D6A6D),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF264345),
    onPrimaryContainer = Color(0xFFC3DCDD),

    secondary = Color(0xFF99B1B3),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF3D5051),
    onSecondaryContainer = Color(0xFFD0E4E4),

    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF4A2530),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD9E0) ,

    background = Color(0xFF0B1212),       // deeper dark teal-black tone
    onBackground = Color(0xFFDDEBEB),     // slightly softened light text

    surface = Color(0xFF111C1C),          // even darker than before, close to background but still distinct
    onSurface = Color(0xFFDDEBEB),        // same as onBackground for consistency

    error = Color(0xFFE02929),
    onError = Color.White
)


val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4D6A6D),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC3DCDD),
    onPrimaryContainer = Color(0xFF1A2F31),

    secondary = Color(0xFF556E70),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD0E4E4),
    onSecondaryContainer = Color(0xFF263B3C),

    tertiary = Color(0xFFF2B7C6) ,
    onTertiary = Color(0xFF3E1C1E),
    tertiaryContainer = Color(0xFFFFD9E0),
    onTertiaryContainer = Color(0xFF410006),

    background = Color(0xFFF5F9F9),
    onBackground = Color(0xFF1E2A2B),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1E2A2B),

    error = Color(0xFFBA1A1A),
    onError = Color.White
)




// Typography
val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Thin),       // 100
    Font(R.font.poppins_extralight, FontWeight.ExtraLight), // 200
    Font(R.font.poppins_light, FontWeight.Light),     // 300
    Font(R.font.poppins_regular, FontWeight.Normal),  // 400
    Font(R.font.poppins_medium, FontWeight.Medium),   // 500
    Font(R.font.poppins_semibold, FontWeight.SemiBold), // 600
    Font(R.font.poppins_bold, FontWeight.Bold),       // 700
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold), // 800
    Font(R.font.poppins_black, FontWeight.Black)      // 900
)

val AppTypography = Typography(

    displayLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold // Main large headers
    ),
    displayMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium
    ),
    displaySmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium // Was Normal before
    ),
    headlineLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    ),
    headlineSmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    labelLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    ),
    labelMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold // Was Medium before
    ),
    labelSmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontSize = 8.sp,
        fontWeight = FontWeight.Normal
    )
)


@Composable
fun FairSplitTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme,
//        colorScheme = LightColorScheme,
        typography = AppTypography,  // Ensure your typography is set correctly
        content = content
    )
}