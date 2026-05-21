package com.settlr.app.util

import kotlin.math.abs

fun formatAmount(amount: Double): String {
    val absAmount = abs(amount)
    return if (absAmount % 1.0 == 0.0) {
        absAmount.toLong().toString()
    } else {
        String.format(java.util.Locale.US, "%.2f", absAmount)
    }
}
