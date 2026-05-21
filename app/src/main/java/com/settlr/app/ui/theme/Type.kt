@file:OptIn(androidx.compose.ui.text.ExperimentalTextApi::class)
package com.settlr.app.ui.theme

import android.os.Build
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.settlr.app.R

val RobotoFlex = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    FontFamily(
        Font(
            R.font.roboto_flex,
            weight = FontWeight.Light,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(300),
                FontVariation.Setting("opsz", 72f)
            )
        ),
        Font(
            R.font.roboto_flex,
            weight = FontWeight.Normal,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(400),
                FontVariation.Setting("opsz", 40f)
            )
        ),
        Font(
            R.font.roboto_flex,
            weight = FontWeight.Medium,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(500),
                FontVariation.Setting("opsz", 24f)
            )
        ),
        Font(
            R.font.roboto_flex,
            weight = FontWeight.Bold,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(700),
                FontVariation.Setting("opsz", 72f)
            )
        )
    )
} else {
    FontFamily(
        Font(R.font.roboto_flex, weight = FontWeight.Light),
        Font(R.font.roboto_flex, weight = FontWeight.Normal),
        Font(R.font.roboto_flex, weight = FontWeight.Medium),
        Font(R.font.roboto_flex, weight = FontWeight.Bold),
    )
}

val GoogleSansFlex = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    FontFamily(
        Font(
            R.font.google_sans_flex,
            weight = FontWeight.Normal,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(400),
                FontVariation.Setting("opsz", 14f),
                FontVariation.Setting("ROND", 100f)
            )
        ),
        Font(
            R.font.google_sans_flex,
            weight = FontWeight.Medium,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(500),
                FontVariation.Setting("opsz", 14f),
                FontVariation.Setting("ROND", 100f)
            )
        ),
        Font(
            R.font.google_sans_flex,
            weight = FontWeight.SemiBold,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(600),
                FontVariation.Setting("opsz", 16f),
                FontVariation.Setting("ROND", 100f)
            )
        ),
        Font(
            R.font.google_sans_flex,
            weight = FontWeight.Bold,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(700),
                FontVariation.Setting("opsz", 16f),
                FontVariation.Setting("ROND", 100f)
            )
        )
    )
} else {
    FontFamily(
        Font(R.font.google_sans_flex, weight = FontWeight.Normal),
        Font(R.font.google_sans_flex, weight = FontWeight.Medium),
        Font(R.font.google_sans_flex, weight = FontWeight.SemiBold),
        Font(R.font.google_sans_flex, weight = FontWeight.Bold),
    )
}

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.1).sp
    ),
    titleMedium = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.05).sp
    ),
    titleSmall = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = GoogleSansFlex,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    )
)