package com.settlr.app.data.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Entry(
    val id: String = UUID.randomUUID().toString(),
    val personId: String,
    val amount: Double,
    val note: String = "",
    val isOwedToMe: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val isSettled: Boolean = false
)
