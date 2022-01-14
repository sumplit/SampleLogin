package id.test.tada.data.local

import android.content.Context
import androidx.room.*
import id.test.tada.data.local.model.User
import id.test.tada.data.local.model.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class DataDbSource : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: DataDbSource? = null

        fun getInstance(context: Context): DataDbSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                DataDbSource::class.java, "tada-test.db"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}