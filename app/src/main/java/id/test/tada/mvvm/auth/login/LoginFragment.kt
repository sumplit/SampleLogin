package id.test.tada.mvvm.auth.login

import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import id.test.tada.R
import id.test.tada.base.BaseFragment
import id.test.tada.databinding.FragmentLoginBinding
import id.test.tada.mvvm.auth.register.RegisterActivity
import id.test.tada.mvvm.main.MainActivity
import id.test.tada.util.gone
import id.test.tada.util.obtainViewModel
import id.test.tada.util.showSnackbarError

class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun obtainVm(): LoginViewModel {
        return obtainViewModel(LoginViewModel::class.java).apply {

            successLogin.observe(viewLifecycleOwner, {
                MainActivity.startThisActivity(requireContext())
            })

            eventMessage.observe(viewLifecycleOwner, {
                mViewBinding.root.showSnackbarError(it, Snackbar.LENGTH_SHORT)
            })

            isRequesting.observe(viewLifecycleOwner, {
                if (it) {
                    mViewBinding.vfLogin.displayedChild = 1
                } else mViewBinding.vfLogin.displayedChild = 0
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()

    }

    private fun setView() {
        mViewBinding.apply {

            textCustom()

            iToolbarLogin.apply {
                tvToolbar.text = getString(R.string.login)
                ivBack.gone()
            }

            btnLogin.setOnClickListener {

                val username = edtInputUsername.text.toString()
                val password = edtInputPassword.text.toString()

                if (username.isEmpty()) {
                    txtLayoutUsername.isErrorEnabled = true
                    txtLayoutUsername.error = getString(R.string.warning_username_empty)
                }

                if (password.isEmpty()) {
                    txtLayoutPassword.isErrorEnabled = true
                    txtLayoutPassword.error = getString(R.string.warning_password_empty)
                }

                if (username.isNotEmpty() && password.isNotEmpty())
                    myVm.processLogin(username, password)
            }
        }
    }

    private fun textCustom() {
        val account = getString(R.string.dont_have_account)
        val spanString = SpannableString(account)

        val question = object : ClickableSpan() {
            override fun onClick(p0: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

        }

        val register = object : ClickableSpan() {
            override fun onClick(p0: View) {
                RegisterActivity.startThisActivity(requireContext())
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ContextCompat.getColor(requireContext(), R.color.colord84372)
            }
        }

        spanString.setSpan(question, 0, account.length - 8, 0)
        spanString.setSpan(register, account.length - 8, account.length, 0)
        mViewBinding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
        mViewBinding.tvRegister.setText(spanString, TextView.BufferType.SPANNABLE)
    }

}
