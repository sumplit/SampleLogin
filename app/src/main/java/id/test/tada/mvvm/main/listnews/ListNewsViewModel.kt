package id.test.tada.mvvm.main.listnews

import android.app.Application
import id.test.tada.base.BaseCallback
import id.test.tada.base.BaseViewModel
import id.test.tada.data.jobs.Repository
import id.test.tada.data.remote.model.article.ArticleResponse
import id.test.tada.util.SingleLiveEvent

class ListNewsViewModel(val context: Application, val repo: Repository) : BaseViewModel(context) {

    val mListArticle = SingleLiveEvent<List<NewsModel>>()
    val mArticle = SingleLiveEvent<ArticleResponse>()

    fun getListArticles() {

        repo.getAllListArticle(callback = object : BaseCallback<List<ArticleResponse>> {
            override fun showProgress(show: Boolean) {
                isRequesting.value = show
            }

            override fun onError(code: Int, message: String) {
                eventMessage.value = message
            }

            override fun onSuccess(response: List<ArticleResponse>?, code: Int, message: String) {
                mappingDataArticle(response)
            }

        })
    }

    private fun mappingDataArticle(response: List<ArticleResponse>?) {
        mListArticle.value = response?.map {
            NewsModel(
                title = it.title.orEmpty(),
                image = it.urlToImage,
                mArticleResponse = it
            )
        }
    }

    fun detailArticle(mArticleResponse: ArticleResponse?){
        mArticle.value = mArticleResponse
    }

}
