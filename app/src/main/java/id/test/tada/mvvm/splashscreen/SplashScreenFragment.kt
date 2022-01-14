package id.test.tada.mvvm.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import id.test.tada.base.BaseFragment
import id.test.tada.databinding.FragmentSplashScreenBinding
import id.test.tada.mvvm.auth.login.LoginActivity
import id.test.tada.mvvm.main.MainActivity
import id.test.tada.util.obtainViewModel

class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>(FragmentSplashScreenBinding::inflate) {

    companion object {
        fun newInstance() = SplashScreenFragment()
    }

    override fun obtainVm(): SplashScreenViewModel {
        return obtainViewModel(SplashScreenViewModel::class.java).apply {

            Handler(Looper.getMainLooper()).postDelayed({
                checkIsLogin()
            }, 1000)

            isLogin.observe(viewLifecycleOwner, {
                if (it)
                    MainActivity.startThisActivity(requireContext())
                else LoginActivity.startThisActivity(requireContext())
            })

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()

    }

    private fun setView() {
        mViewBinding.apply {

        }
    }

}
