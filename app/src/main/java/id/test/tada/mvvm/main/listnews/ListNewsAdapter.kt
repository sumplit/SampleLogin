package id.test.tada.mvvm.main.listnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.test.tada.databinding.ItemListNewsBinding
import id.test.tada.util.processImage

class ListNewsAdapter(val mViewModel: ListNewsViewModel) :
    RecyclerView.Adapter<ListNewsAdapter.ItemNewsHolder>() {

    val mData = arrayListOf<NewsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNewsHolder {
        return ItemNewsHolder(
            ItemListNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ItemNewsHolder, position: Int) {
        holder.bind(mData[position], mViewModel)
    }

    fun replace(data: List<NewsModel>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    class ItemNewsHolder(itemView: ItemListNewsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val mBinding = itemView

        fun bind(data: NewsModel, mViewModel: ListNewsViewModel) {
            mBinding.apply {
                tvTitle.text = data.title
                ivNews.processImage(progressBar, data.image.orEmpty(), {})
                root.setOnClickListener {
                    mViewModel.detailArticle(data.mArticleResponse)
                }
            }
        }
    }

    override fun getItemCount() = mData.size
}