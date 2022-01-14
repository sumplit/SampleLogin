package id.test.tada.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.test.tada.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val supervisorJob = SupervisorJob()
    val isRequesting = SingleLiveEvent<Boolean>()
    val eventMessage = SingleLiveEvent<String>()
    val logoutEvent = SingleLiveEvent<Void>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + supervisorJob

    open fun start() {}
    open fun onClearDisposable() {
        supervisorJob.cancel()
    }

}