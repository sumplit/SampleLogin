package id.test.tada.mvvm.splashscreen

import android.app.Application
import id.test.tada.base.BaseCallback
import id.test.tada.base.BaseViewModel
import id.test.tada.data.jobs.Repository
import id.test.tada.data.local.model.User
import id.test.tada.util.SingleLiveEvent

class SplashScreenViewModel(val context: Application, val repo: Repository) :
    BaseViewModel(context) {

    val isLogin = SingleLiveEvent<Boolean>()

    fun checkIsLogin() {

        repo.isLoginUser(
            callback = object : BaseCallback<User> {
                override fun showProgress(show: Boolean) {
                    isRequesting.value = show
                }

                override fun onError(code: Int, message: String) {
                    eventMessage.value = message
                }

                override fun onSuccess(response: User?, code: Int, message: String) {
                    isLogin.value = response != null
                }

            })
    }

}
