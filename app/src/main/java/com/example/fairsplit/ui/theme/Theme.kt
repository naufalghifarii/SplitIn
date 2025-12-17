package com.example.fairsplit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
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


// Warna dan tema aplikasi (Light / Dark)
// Nilai-nilai di sini menentukan palet utama yang dipakai seluruh aplikasi.
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



// Typography (poppins font family) digunakan di beberapa gaya teks khusus
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


/**
 * Tema utama aplikasi yang memilih colorScheme dan typography berdasarkan preferensi.
 */
@Composable
fun FairSplitTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme,
        typography = AppTypography,
        content = content
    )
}