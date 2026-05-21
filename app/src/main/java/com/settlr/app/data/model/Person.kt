package com.settlr.app.data.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Person(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val avatarColor: Int,
    val createdAt: Long = System.currentTimeMillis()
)
