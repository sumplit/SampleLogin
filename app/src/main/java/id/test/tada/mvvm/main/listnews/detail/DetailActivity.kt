package id.test.tada.mvvm.main.listnews.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.test.tada.R
import id.test.tada.data.remote.model.article.ArticleResponse
import id.test.tada.databinding.ActivityDetailNewsBinding
import id.test.tada.util.processImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsBinding
    var mArticleResponse: ArticleResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mArticleResponse =
            Gson().fromJson(intent.getStringExtra(DATA_NEWS), ArticleResponse::class.java)

        setView()
    }

    private fun setView() {
        binding.apply {

            iToolbarDetail.apply {
                tvToolbar.text = getString(R.string.detail)
                ivBack.setOnClickListener {
                    onBackPressed()
                }
            }

            ivNews.processImage(progressBar, mArticleResponse?.urlToImage.orEmpty(), {})
            tvTitle.text = mArticleResponse?.title.orEmpty()
        }
    }


    companion object {

        const val DATA_NEWS = "data"

        fun startThisActivity(contex: Context, articleResponse: ArticleResponse) {
            contex.startActivity(Intent(contex, DetailActivity::class.java).apply {
                putExtra(DATA_NEWS, Gson().toJson(articleResponse))
            })
        }
    }
}