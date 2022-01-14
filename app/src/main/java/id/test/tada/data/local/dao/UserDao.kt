package id.test.tada.data.local.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert
    fun saveUser(user: User)

    @Query("SELECT * FROM USER_TABLE_DB WHERE username=:username AND password=:password")
    fun checkLoginUserData(username: String, password: String): Maybe<User>

    @Query("SELECT * FROM USER_TABLE_DB WHERE isLogin=1")
    fun isLoginUserData(): Maybe<User>

    @Query("UPDATE USER_TABLE_DB SET isLogin=:isLogin WHERE id=:id")
    fun updateLoginLogout(isLogin: Int, id: Int)

}