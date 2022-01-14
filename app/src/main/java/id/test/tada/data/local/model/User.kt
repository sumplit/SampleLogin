package id.test.tada.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table_db")
data class User(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "username")
    var username: String?,

    @ColumnInfo(name = "password")
    var password: String?,

    @ColumnInfo(name = "isLogin")
    var isLogin: Int? = 0

)