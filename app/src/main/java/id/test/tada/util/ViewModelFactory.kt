package id.test.tada.util

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.test.tada.data.jobs.Repository
import id.test.tada.mvvm.auth.login.LoginViewModel
import id.test.tada.mvvm.auth.register.RegisterViewModel
import id.test.tada.mvvm.main.MainViewModel
import id.test.tada.mvvm.main.listnews.ListNewsViewModel
import id.test.tada.mvvm.main.profile.ProfileViewModel
import id.test.tada.mvvm.splashscreen.SplashScreenViewModel

class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(RegisterViewModel::class.java) ->
                    RegisterViewModel(mApplication, repository)
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(mApplication, repository)
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(mApplication, repository)
                isAssignableFrom(ListNewsViewModel::class.java) ->
                    ListNewsViewModel(mApplication, repository)
                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(mApplication, repository)
                isAssignableFrom(SplashScreenViewModel::class.java) ->
                    SplashScreenViewModel(mApplication, repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(mApplication: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    mApplication,
                    Injection.provideRepository(mApplication)
                )
                    .also { INSTANCE = it }
            }
    }
}