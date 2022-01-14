package id.test.tada.data.remote

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import id.test.tada.base.BaseApiModel
import id.test.tada.data.remote.model.article.ArticleResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("top-headlines?country=us&category=business")
    fun getAllArticles(
        @Query("apiKey") apiKey: String
    ): Observable<BaseApiModel<List<ArticleResponse>>>

    companion object Factory {
        fun createService(baseUrl: String, context: Context): ApiService {
            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            //if (BuildConfig.DEBUG) {
            val mClient = OkHttpClient.Builder()
                .addInterceptor(mLoggingInterceptor)
                .addInterceptor(ChuckInterceptor(context))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
            /*    } else {
                    OkHttpClient.Builder()
                            .addInterceptor { chain ->
                                val request = chain.request()
                                request.newBuilder().header("Cache-Control",
                                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                                chain.proceed(request)
                            }
                            .readTimeout(30, TimeUnit.SECONDS)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .build()
                }*/
            val mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClient)
                .build()

            return mRetrofit.create(ApiService::class.java)
        }
    }
}