package id.test.tada.mvvm.main.profile

import android.app.Application
import id.test.tada.base.BaseCallback
import id.test.tada.base.BaseViewModel
import id.test.tada.data.jobs.Repository
import id.test.tada.data.local.model.User
import id.test.tada.data.remote.model.article.ArticleResponse
import id.test.tada.util.SingleLiveEvent

class ProfileViewModel(val context: Application, val repo: Repository) : BaseViewModel(context) {

    val mUser = SingleLiveEvent<User>()
    var id = 0

    fun getUser() {

        repo.isLoginUser(
            callback = object : BaseCallback<User> {
                override fun showProgress(show: Boolean) {
                    isRequesting.value = show
                }

                override fun onError(code: Int, message: String) {
                    eventMessage.value = message
                }

                override fun onSuccess(response: User?, code: Int, message: String) {
                    id = response?.id ?: 0
                    mUser.value = response
                }

            })
    }

    fun updateDataLogin() {
        repo.updateUser(isLogin = 0, id = id, callback = object : BaseCallback<Any> {
            override fun showProgress(show: Boolean) {

            }

            override fun onError(code: Int, message: String) {
                eventMessage.value = message
            }

            override fun onSuccess(response: Any?, code: Int, message: String) {
                logoutEvent.call()
            }

        })
    }
}
