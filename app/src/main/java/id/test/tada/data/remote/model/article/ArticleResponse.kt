package id.test.tada.data.remote.model.article

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @SerializedName("source")
    val source: ArticleSourceResponse,

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("content")
    val content: String?
)

data class ArticleSourceResponse(

    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?
)
