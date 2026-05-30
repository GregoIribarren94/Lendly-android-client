package com.lendly.fintech.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.lendly.fintech.R


// ---------------------------------------------------------------------------
// Familias tipográficas
// ---------------------------------------------------------------------------
val Montserrat = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),     // 400
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),  // 600
    Font(R.font.montserrat_bold, FontWeight.Bold),          // 700
    Font(R.font.montserrat_extrabold, FontWeight.ExtraBold) // 800
)

val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),    // 400
    Font(R.font.inter_medium, FontWeight.Medium),     // 500
    Font(R.font.inter_semibold, FontWeight.SemiBold)  // 600
)

// ---------------------------------------------------------------------------
// Estilos exactos del Figma
// ---------------------------------------------------------------------------

// Onboarding titles — Montserrat ExtraBold 32/35, uppercase, center
val OnboardingTitle = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 32.sp,
    lineHeight = 35.sp,
)

// Success title (ej. "ALL DONE") — Montserrat Bold 36/44, uppercase, center
val DisplayTitle = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.Bold,
    fontSize = 36.sp,
    lineHeight = 44.sp,
)

// Headlines (títulos de pantalla) — Montserrat SemiBold 28/36
val Headline = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp,
    lineHeight = 36.sp,
)

// Subtítulo onboarding — Inter Regular 22/28, center
val OnboardingSubtitle = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 28.sp,
)

// Section title (ej. "Transaction Details") — Inter SemiBold 22/28
val SectionTitle = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp,
    lineHeight = 28.sp,
)

// Body / texto bajo headlines — Inter Regular 16/24, ls 0.5
val Body = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

// Emphasis (ej. "password") — Inter SemiBold 16/24, ls 0.15, center
val BodyEmphasis = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp,
)

// Texto de botón — Inter SemiBold 14/20, ls 0.1, center
val ButtonLabel = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp,
)

// Párrafos chicos — Inter Regular 12/16, ls 0.4
val Caption = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.4.sp
)

// Fechas / navegación — Inter Medium 12/16, ls 0.5
val CaptionMedium = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)

// ---------------------------------------------------------------------------
// Typography de Material 3 (para que los componentes M3 hereden el diseño)
// Cada ranura apunta al estilo del Figma más cercano por tamaño/uso.
// ---------------------------------------------------------------------------
val Typography = Typography(
    displayLarge = OnboardingTitle,
    displaySmall = DisplayTitle,
    headlineMedium = Headline,
    headlineSmall = OnboardingSubtitle,
    titleLarge = SectionTitle,
    titleMedium = BodyEmphasis,
    bodyLarge = Body,
    bodySmall = Caption,
    labelLarge = ButtonLabel,
    labelMedium = CaptionMedium
)