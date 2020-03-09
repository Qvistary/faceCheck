package top.qvisa.faceCheck.model.remote.`interface`

import retrofit2.Response
import retrofit2.http.*
import top.qvisa.faceCheck.model.bean.SignInBean
import top.qvisa.faceCheck.model.bean.SignUpBean

interface SignInterface {

    //登陆
    @GET("login")
    suspend fun signInUser(
        @HeaderMap header: HashMap<String, String>,
        @QueryMap hashMap: HashMap<String, String>
    ): Response<SignInBean>

    //注册
    @POST("users")
    suspend fun signUpUser(
        @HeaderMap header: HashMap<String, String>,
        @Body hashMap: HashMap<String, String>
    ): Response<SignUpBean>

}
