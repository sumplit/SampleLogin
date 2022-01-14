package id.test.tada.mvvm.main.listnews

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import id.test.tada.base.BaseFragment
import id.test.tada.databinding.FragmentListNewsBinding
import id.test.tada.mvvm.main.listnews.detail.DetailActivity
import id.test.tada.util.gone
import id.test.tada.util.obtainViewModel
import id.test.tada.util.visible

class ListNewsFragment :
    BaseFragment<FragmentListNewsBinding, ListNewsViewModel>(FragmentListNewsBinding::inflate) {

    private val adapterNews by lazy { ListNewsAdapter(myVm) }

    override fun obtainVm(): ListNewsViewModel {
        return obtainViewModel(ListNewsViewModel::class.java).apply {

            getListArticles()
            mListArticle.observe(viewLifecycleOwner, {
                (mViewBinding.rvListNews.adapter as ListNewsAdapter).replace(it)
            })

            mArticle.observe(viewLifecycleOwner, {
                DetailActivity.startThisActivity(requireContext(), it)
            })

            isRequesting.observe(viewLifecycleOwner, {
                if (it) {
                    mViewBinding.shimmerFrameLayout.visible()
                } else {
                    mViewBinding.shimmerFrameLayout.gone()
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()

    }

    private fun setView() {
        mViewBinding.apply {
            rvListNews.adapter = adapterNews
            rvListNews.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        fun newInstance() = ListNewsFragment()
    }

}
