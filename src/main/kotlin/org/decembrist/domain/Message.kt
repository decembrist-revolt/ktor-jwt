package org.decembrist.domain

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: Long? = null,
    val data: String,
) {
    constructor(data: String) : this(null, data)
}