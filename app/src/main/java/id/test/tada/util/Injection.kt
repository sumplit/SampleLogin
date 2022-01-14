package id.test.tada.util

import android.content.Context
import androidx.preference.PreferenceManager
import id.test.tada.data.jobs.Repository
import id.test.tada.data.local.LocalDataSource
import id.test.tada.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context) = Repository.getInstance(
        RemoteDataSource.getInstance(context),
        LocalDataSource.getInstance(context, PreferenceManager.getDefaultSharedPreferences(context))
    )
}