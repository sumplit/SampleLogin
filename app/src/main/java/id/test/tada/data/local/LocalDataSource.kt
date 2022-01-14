package id.test.tada.data.local

import android.content.Context
import android.content.SharedPreferences
import id.test.tada.base.BaseCallback
import id.test.tada.data.jobs.DataSource
import id.test.tada.data.local.model.User
import id.test.tada.data.local.model.UserDao
import id.test.tada.data.remote.model.article.ArticleResponse
import io.reactivex.Completable
import io.reactivex.MaybeObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LocalDataSource private constructor(
    private val context: Context,
    private val preferences: SharedPreferences
) :
    DataSource {

    private var compositeDisposable: CompositeDisposable? = null

    private val userDao: UserDao by lazy {
        DataDbSource.getInstance(context).userDao()
    }

    override fun clearDisposable() {
        clearSubscribe()
    }

    override fun saveUser(user: User, callback: BaseCallback<Any>) {
        addDisposable(Completable.fromAction {
            userDao.apply {
                saveUser(user)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSuccess("User is registered")
            }, { error ->
                callback.onError(
                    0,
                    error.message
                        ?: "Authentication failed"
                )
            })
        )
    }

    override fun loginUser(username: String, password: String, callback: BaseCallback<User>) {
        userDao.checkLoginUserData(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.showProgress(true) }
            .subscribe(object : MaybeObserver<User> {
                override fun onError(e: Throwable) {
                    callback.showProgress(false)
                    callback.onError(0, message = e.message ?: "")
                }

                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }

                override fun onSuccess(t: User) {
                    callback.showProgress(false)
                    callback.onSuccess(t)
                }

                override fun onComplete() {
                    callback.onSuccess(null)
                }
            })
    }

    override fun isLoginUser(callback: BaseCallback<User>) {
        userDao.isLoginUserData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.showProgress(true) }
            .subscribe(object : MaybeObserver<User> {
                override fun onError(e: Throwable) {
                    callback.showProgress(false)
                    callback.onError(0, message = e.message ?: "")
                }

                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }

                override fun onSuccess(t: User) {
                    callback.showProgress(false)
                    callback.onSuccess(t)
                }

                override fun onComplete() {
                    callback.onSuccess(null)
                }
            })
    }

    override fun updateUser(isLogin: Int, id: Int, callback: BaseCallback<Any>) {
        addDisposable(Completable.fromAction {
            userDao.apply {
                updateLoginLogout(id = id, isLogin = isLogin)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSuccess("Success")
            }, { error ->
                callback.onError(
                    0,
                    error.message
                        ?: "Failed"
                )
            })
        )
    }

    override fun getAllListArticle(callback: BaseCallback<List<ArticleResponse>>) {
        TODO("Not yet implemented")
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

        private var INSTANCE: LocalDataSource? = null

        @JvmStatic
        fun getInstance(context: Context, preferences: SharedPreferences): LocalDataSource {
            if (INSTANCE == null) {
                synchronized(LocalDataSource::javaClass) {
                    INSTANCE = LocalDataSource(context, preferences)
                }
            }
            return INSTANCE!!
        }

        fun clearInstance() {
            INSTANCE = null
        }
    }
}