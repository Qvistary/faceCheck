package top.qvisa.faceCheck.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response
import top.qvisa.faceCheck.BuildConfig
import top.qvisa.faceCheck.model.bean.SignInBean
import top.qvisa.faceCheck.model.bean.SignUpBean
import top.qvisa.faceCheck.model.local.dao.UserDao
import top.qvisa.faceCheck.model.local.entity.User
import top.qvisa.faceCheck.model.remote.SignClient

class SignRepository(private val userDao: UserDao) {

    private val tag = "DATA_SOURCE"

    interface DataListener {
        suspend fun fromData(code: Int)
    }

    companion object {
        private const val GET_DATA_FROM_ROOM = 520
        private const val GET_DATA_ERROR = 0
    }

    suspend fun signIn(userId: String, password: String, dataListener: DataListener) {
        val num = getUser(userId)
        if (num == 0)
            try {
                requestSignIn(userId, password).apply {
                    dataListener.fromData(this.code())
                    if (this.code() == 200) {
                        insertUser(
                            User(
                                userId,
                                this.body()!!.trueName,
                                password
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                dataListener.fromData(GET_DATA_ERROR)
            }
        else if (num == 1) {
            if (getPassword(userId) == password) dataListener.fromData(GET_DATA_FROM_ROOM)
            else dataListener.fromData(400)
        }
    }

    suspend fun signUp(
        userId: String, password: String, username: String, dataListener: DataListener
    ) {
        try {
            requestSignUp(userId, password, username).code()
                .apply {
                    dataListener.fromData(this)
                    if (this == 201) insertUser(User(userId, username, password))
                }
        } catch (e: Exception) {
            dataListener.fromData(GET_DATA_ERROR)
        }
    }

    private suspend fun insertUser(user: User) {
        userDao.insertUser(user)
        Log.d(tag, "插入数据库")
    }

    private fun getUser(id: String): Int {
        return userDao.getUser(id)
    }

    private fun getPassword(id: String): String {
        return userDao.getPassword(id)
    }


    private val headers = hashMapOf(
        "X-Bmob-Application-Id" to "4576b9e69a16901c5059320bb80b1d50",
        "X-Bmob-REST-API-Key" to BuildConfig.Bmob_API_Key,
        "Content-Type" to "application/json"
    )

    private suspend fun requestSignIn(username: String, password: String): Response<SignInBean> {
        val hashMap = hashMapOf(
            "username" to username,
            "password" to password
        )
        return SignClient.reqApi.signInUser(headers, hashMap)
    }

    private suspend fun requestSignUp(
        username: String, password: String, trueName: String
    ): Response<SignUpBean> {
        val hashMap = hashMapOf(
            "username" to username, "password" to password, "trueName" to trueName
        )
        return SignClient.reqApi.signUpUser(headers, hashMap)
    }

}