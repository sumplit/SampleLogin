package id.test.tada.base

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ContentFrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import id.test.tada.mvvm.auth.login.LoginActivity
import id.test.tada.util.showSnackbar

abstract class BaseFragment<VB : ViewBinding, T : BaseViewModel>(
    private val viewDataBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    lateinit var myVm: T
    abstract fun obtainVm(): T
    lateinit var mViewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myVm = obtainVm().apply {
            eventMessage.observe(this@BaseFragment.viewLifecycleOwner, Observer {
                if (!it.isNullOrEmpty()) {
                    val container =
                        requireActivity().findViewById<ContentFrameLayout>(R.id.content)
                    container.showSnackbar(it, Snackbar.LENGTH_LONG)
                }
            })

            logoutEvent.observe(this@BaseFragment.viewLifecycleOwner, Observer {
                LoginActivity.startThisActivity(requireActivity())
            })

            start()
        }
        mViewBinding = viewDataBinding.invoke(
            LayoutInflater.from(container?.context),
            container,
            false
        )

        return mViewBinding.root
    }

}