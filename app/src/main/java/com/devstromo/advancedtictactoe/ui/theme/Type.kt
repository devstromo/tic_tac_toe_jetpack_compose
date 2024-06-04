package com.devstromo.advancedtictactoe.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.devstromo.advancedtictactoe.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Press Start 2P")
val labelFontName = GoogleFont("Poppins")

val fontFamily = FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider
    )
)

val labelFontFamily = FontFamily(
    Font(
        googleFont = labelFontName,
        fontProvider = provider
    )
)
private val defaultTypography = Typography()
val Typography = Typography(
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = fontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = fontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = fontFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = fontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = fontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = fontFamily),

    displayLarge = defaultTypography.displayLarge.copy(fontFamily = fontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = fontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = fontFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = labelFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = labelFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = labelFontFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = fontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = fontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = fontFamily),
)