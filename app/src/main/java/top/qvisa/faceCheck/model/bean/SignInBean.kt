package top.qvisa.faceCheck.model.bean

data class SignInBean(
    val createdAt: String,
    val objectId: String,
    val sessionToken: String,
    val trueName: String,
    val updatedAt: String,
    val username: String
)