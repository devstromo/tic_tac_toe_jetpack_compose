package com.devstromo.advancedtictactoe.config.helpers

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PointF
import androidx.compose.ui.geometry.Offset
import androidx.core.graphics.plus
import androidx.core.graphics.times
import java.util.Locale
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal fun Float.toRadians() = this * PI.toFloat() / 180f

internal fun Offset.rotate90() = Offset(-y, x)

internal fun directionVector(angleRadians: Float) = Offset(cos(angleRadians), sin(angleRadians))

internal fun Offset.rotate(angleRadians: Float): Offset {
    val vec = directionVector(angleRadians)
    return vec * x + vec.rotate90() * y
}

internal val PointZero = PointF(0f, 0f)

internal fun radialToCartesian(
    radius: Float,
    angleRadians: Float,
    center: PointF = PointZero
) = directionVectorPointF(angleRadians) * radius + center


internal fun directionVectorPointF(angleRadians: Float) =
    PointF(cos(angleRadians), sin(angleRadians))

@Suppress("DEPRECATION")
fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources = context.resources
    val config = resources.configuration
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)

    val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    prefs.edit().putString("app_language", languageCode).apply()
}
