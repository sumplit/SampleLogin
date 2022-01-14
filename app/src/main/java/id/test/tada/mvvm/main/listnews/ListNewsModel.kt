package id.test.tada.mvvm.main.listnews

import id.test.tada.data.remote.model.article.ArticleResponse

data class NewsModel(
    val image: String? = "",
    val title: String = "",
    val mArticleResponse: ArticleResponse? = null
)
