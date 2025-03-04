package com.example.rickmorty.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.rickmorty.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val robotoMonoFont = GoogleFont(name = "Roboto Mono")
val robotoMonoFontFamily = FontFamily(
    Font(googleFont = robotoMonoFont, fontProvider = provider)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = robotoMonoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
),
    displayMedium = TextStyle(
        fontFamily = robotoMonoFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 32.sp,
        lineHeight = 40.sp,
),
    displaySmall = TextStyle(
        fontFamily = robotoMonoFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
)