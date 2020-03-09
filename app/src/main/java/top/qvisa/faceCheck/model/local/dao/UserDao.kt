package top.qvisa.faceCheck.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import top.qvisa.faceCheck.model.local.entity.User

@Dao
interface UserDao {
    @Query("SELECT COUNT(*) from table_user where userId = :id ")
    fun getUser(id: String): Int

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT userName from table_user where userId = :id")
    fun getName(id: String): String

    @Query("SELECT userPassword from table_user where userId = :id")
    fun getPassword(id: String): String
}