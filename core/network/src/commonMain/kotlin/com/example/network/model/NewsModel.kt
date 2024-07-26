package domain.models.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsModel(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: Long? = null
)
