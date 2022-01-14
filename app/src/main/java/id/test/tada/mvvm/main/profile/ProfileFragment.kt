package id.test.tada.mvvm.main.profile

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import id.test.tada.base.BaseFragment
import id.test.tada.databinding.FragmentLoginBinding
import id.test.tada.databinding.FragmentProfileBinding
import id.test.tada.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_list_news.*

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(FragmentProfileBinding::inflate) {


    override fun obtainVm(): ProfileViewModel {
        return obtainViewModel(ProfileViewModel::class.java).apply {

            getUser()

            mUser.observe(viewLifecycleOwner, {
                setNameUser(it.username.orEmpty())
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()

    }

    private fun setView() {
        mViewBinding.apply {
            btnLogout.setOnClickListener {
                myVm.updateDataLogin()
            }
        }
    }

    private fun setNameUser(name: String) {
        mViewBinding.tvName.text = name
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

}
