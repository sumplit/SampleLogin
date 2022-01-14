package id.test.tada.mvvm.auth.login

import android.app.Application
import id.test.tada.R
import id.test.tada.base.BaseCallback
import id.test.tada.base.BaseViewModel
import id.test.tada.data.jobs.Repository
import id.test.tada.data.local.model.User
import id.test.tada.util.SingleLiveEvent

class LoginViewModel(val context: Application, val repo: Repository) : BaseViewModel(context) {

    val successLogin = SingleLiveEvent<Void>()

    fun processLogin(username: String, password: String) {

        repo.loginUser(
            username = username,
            password = password,
            callback = object : BaseCallback<User> {
                override fun showProgress(show: Boolean) {
                    isRequesting.value = show
                }

                override fun onError(code: Int, message: String) {
                    eventMessage.value = message
                }

                override fun onSuccess(response: User?, code: Int, message: String) {
                    if (response != null)
                        updateDataLogin(response)
                    else eventMessage.value = context.getString(R.string.authentication_failed)
                }

            })
    }

    private fun updateDataLogin(response: User?) {
        repo.updateUser(isLogin = 1, id = response?.id ?: 0, callback = object : BaseCallback<Any> {
            override fun showProgress(show: Boolean) {

            }

            override fun onError(code: Int, message: String) {
                eventMessage.value = message
            }

            override fun onSuccess(response: Any?, code: Int, message: String) {
                successLogin.call()
            }

        })
    }
}
