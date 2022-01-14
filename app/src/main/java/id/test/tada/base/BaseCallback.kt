package id.test.tada.base

interface BaseCallback<T> {
    fun showProgress(show: Boolean)
    fun onError(code: Int, message: String)
    fun onSuccess(response: T?, code: Int = 0, message: String = "")

}