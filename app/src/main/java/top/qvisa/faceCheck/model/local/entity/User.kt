package top.qvisa.faceCheck.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_user")
data class User(
    @PrimaryKey
    val userId: String,
    val userName: String = "",
    val userPassword: String
)