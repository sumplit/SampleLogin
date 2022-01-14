package id.test.tada.data.jobs

import id.test.tada.base.BaseCallback
import id.test.tada.data.local.model.User
import id.test.tada.data.remote.model.article.ArticleResponse

interface DataSource {


    fun clearDisposable()

    fun saveUser(user: User, callback: BaseCallback<Any>)

    fun loginUser(username: String, password: String, callback: BaseCallback<User>)

    fun isLoginUser(callback: BaseCallback<User>)

    fun updateUser(isLogin: Int, id: Int, callback: BaseCallback<Any>)

    fun getAllListArticle(callback: BaseCallback<List<ArticleResponse>>)
}