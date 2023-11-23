package myplayground.example.learningq.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    val id: Int,
    val link: String,
    val title: String,
    val image: String,
    val headline: String,
    val category: String,

    @SerialName("pusblised_at")
    val publishedAt: String,

    @SerialName("premium_badge")
    val premiumBadge: String,

    @SerialName("detail_post")
    val detailPost: NewsDetail? = null,
)

@Serializable
data class NewsDetail(
    val title: String,
    val image: String,
    val author: String,
    val location: String,

    @SerialName("image_desc")
    val imageDesc: String,

    @SerialName("post_content")
    val postContent: String,

    @SerialName("pusblised_at")
    val publishedAt: String,
)