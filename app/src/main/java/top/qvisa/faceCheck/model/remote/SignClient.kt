package top.qvisa.faceCheck.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.qvisa.faceCheck.model.remote.`interface`.SignInterface

class SignClient{
    companion object  {
        private const val base_url = "https://api2.bmob.cn/1/"
        val reqApi: SignInterface by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return@lazy retrofit.create(SignInterface::class.java)
        }
    }
}
