package id.test.tada.data.remote

import android.content.Context
import id.test.tada.BuildConfig
import id.test.tada.base.BaseApiModel
import id.test.tada.base.BaseCallback
import id.test.tada.data.jobs.DataSource
import id.test.tada.data.local.model.User
import id.test.tada.data.remote.model.article.ArticleResponse
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RemoteDataSource private constructor(
    private val context: Context
) : DataSource {

    var compositeDisposable: CompositeDisposable? = null
    val generalApiService = ApiService.createService(BuildConfig.BASE_URL, context)

    override fun clearDisposable() {
        clearSubscribe()
    }

    override fun saveUser(user: User, callback: BaseCallback<Any>) {
        TODO("Not yet implemented")
    }

    override fun loginUser(username: String, password: String, callback: BaseCallback<User>) {
        TODO("Not yet implemented")
    }

    override fun isLoginUser(callback: BaseCallback<User>) {
        TODO("Not yet implemented")
    }

    override fun updateUser(isLogin: Int, id: Int, callback: BaseCallback<Any>) {
        TODO("Not yet implemented")
    }

    override fun getAllListArticle(callback: BaseCallback<List<ArticleResponse>>) {
        generalApiService.getAllArticles(BuildConfig.API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.showProgress(true) }
            .doOnTerminate { callback.showProgress(false) }
            .subscribe(object : ApiCallback<BaseApiModel<List<ArticleResponse>>>() {
                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onError(code, errorMessage)
                }

                override fun onFinish() {
                }

                override fun onStartObserver(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(model: BaseApiModel<List<ArticleResponse>>) {
                    callback.onSuccess(model.data, message = model.message ?: "")
                }
            })

    }

    private fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
            compositeDisposable!!.add(disposable)
        }
    }

    private fun clearSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable!!.clear()
        }
    }


    companion object {

        private var INSTANCE: RemoteDataSource? = null

        @JvmStatic
        fun getInstance(context: Context): RemoteDataSource {
            if (INSTANCE == null) {
                synchronized(RemoteDataSource::javaClass) {
                    INSTANCE = RemoteDataSource(context)
                }
            }
            return INSTANCE!!
        }

        fun clearInstance() {
            INSTANCE = null
        }
    }

}