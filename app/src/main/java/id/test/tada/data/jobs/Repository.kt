package id.test.tada.data.jobs

import id.test.tada.base.BaseCallback
import id.test.tada.data.local.model.User
import id.test.tada.data.remote.model.article.ArticleResponse

open class Repository(
    val remoteDataSource: DataSource,
    val localDataSource: DataSource
) : DataSource {

    override fun clearDisposable() {
        remoteDataSource.clearDisposable()
    }

    override fun saveUser(user: User, callback: BaseCallback<Any>) {
        localDataSource.saveUser(user, callback)
    }

    override fun loginUser(username: String, password: String, callback: BaseCallback<User>) {
        localDataSource.loginUser(username, password, callback)
    }

    override fun isLoginUser(callback: BaseCallback<User>) {
        localDataSource.isLoginUser(callback)
    }

    override fun updateUser(isLogin: Int, id: Int, callback: BaseCallback<Any>) {
        localDataSource.updateUser(isLogin, id, callback)
    }

    override fun getAllListArticle(callback: BaseCallback<List<ArticleResponse>>) {
        remoteDataSource.getAllListArticle(callback)
    }

    companion object {
        private var INSTANCE: Repository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param remoteDataSource backend data source
         * *
         * @return the [Repository] instance
         */
        @JvmStatic
        fun getInstance(remoteDataSource: DataSource, localDataSource: DataSource) =
            INSTANCE
                ?: synchronized(Repository::class.java) {
                    INSTANCE
                        ?: Repository(remoteDataSource, localDataSource)
                            .also { INSTANCE = it }
                }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}