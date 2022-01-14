package id.test.tada.mvvm.auth.register

import android.app.Application
import id.test.tada.base.BaseCallback
import id.test.tada.base.BaseViewModel
import id.test.tada.data.jobs.Repository
import id.test.tada.data.local.model.User
import id.test.tada.util.SingleLiveEvent

class RegisterViewModel(val context: Application, val repo: Repository) : BaseViewModel(context) {

    val successRegister = SingleLiveEvent<Void>()

    fun processRegister(username: String, password: String) {

        repo.saveUser(
            User(id = null, username = username, password = password),
            object : BaseCallback<Any> {
                override fun showProgress(show: Boolean) {
                    isRequesting.value = show
                }

                override fun onError(code: Int, message: String) {
                    eventMessage.value = message
                }

                override fun onSuccess(response: Any?, code: Int, message: String) {
                    successRegister.call()
                }

            }
        )
    }

}
