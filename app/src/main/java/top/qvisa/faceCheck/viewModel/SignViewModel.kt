package top.qvisa.faceCheck.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.qvisa.faceCheck.model.SignRepository
import top.qvisa.faceCheck.model.local.db.UserDatabase
import top.qvisa.faceCheck.model.local.entity.User

class SignViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = "errorCode"

    val resultCode: LiveData<Int> by lazy {
        _resultCode
    }

    val userId: LiveData<String> by lazy {
        _userId
    }


    private val userDao = UserDatabase.getDatabase(getApplication()).userDao()
    private val repository: SignRepository = SignRepository(userDao)

    private val _userId: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    private val _resultCode: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun setUserId(userId: String) = _userId.postValue(userId)

    fun setResultCode() = _resultCode.postValue(-1)


    fun signIn(userId: String, password: String) {
        viewModelScope.launch(IO) {
            repository.signIn(userId, password, object : SignRepository.DataListener {
                override suspend fun fromData(code: Int) {
                    withContext(Main) {
                        _resultCode.postValue(code)
                    }
                }
            })
        }
    }

    fun signUp(userId: String, password: String, username: String) {
        viewModelScope.launch(IO) {
            repository.signUp(userId, password, username, object : SignRepository.DataListener {
                override suspend fun fromData(code: Int) {
                    withContext(Main) {
                        _resultCode.postValue(code)
                    }
                }
            })
        }
    }


}