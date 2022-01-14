package id.test.tada.mvvm.auth.register

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import id.test.tada.R
import id.test.tada.base.BaseFragment
import id.test.tada.databinding.FragmentRegisterBinding
import id.test.tada.util.active
import id.test.tada.util.inActive
import id.test.tada.util.obtainViewModel
import id.test.tada.util.other.addAfterTextChangedWatcher

class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, RegisterViewModel>(FragmentRegisterBinding::inflate) {

    override fun obtainVm(): RegisterViewModel {
        return obtainViewModel(RegisterViewModel::class.java).apply {
            successRegister.observe(viewLifecycleOwner, {
                setDialogSuccess()
            })

            isRequesting.observe(viewLifecycleOwner, {
                if (it) {
                    mViewBinding.vfRegister.displayedChild = 1
                } else mViewBinding.vfRegister.displayedChild = 0
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validation()

        setView()
    }

    private fun setView() {
        mViewBinding.apply {

            iToolbarRegister.apply {
                tvToolbar.text = getString(R.string.register)
                ivBack.setOnClickListener {
                    requireActivity().onBackPressed()
                }
            }

            edtInputPassword.addAfterTextChangedWatcher {
                validation()
            }

            edtInputUsername.addAfterTextChangedWatcher {
                validation()
            }

            cbAgree.setOnCheckedChangeListener { buttonView, isChecked ->
                validation()
            }

            btnRegister.setOnClickListener {
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
                    myVm.processRegister(username, password)

            }
        }
    }

    private fun validation() {
        if (mViewBinding.edtInputUsername.text.toString().isNotEmpty() &&
            mViewBinding.edtInputPassword.text.toString()
                .isNotEmpty() && mViewBinding.cbAgree.isChecked
        ) {
            mViewBinding.btnRegister.active()
        } else {
            mViewBinding.btnRegister.inActive()
        }
    }

    private fun setDialogSuccess() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.success_register)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.yes) { dialog, _ ->
            requireActivity().finish()
        }
        val alert = builder.create()
        alert.show()
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }

}
