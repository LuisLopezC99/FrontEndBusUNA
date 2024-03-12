package develpment.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import develpment.myapplication.model.EmailUser
import develpment.myapplication.model.UserSignupInput
import develpment.myapplication.model.UserChangePassword
import develpment.myapplication.model.UserLoginResult
import develpment.myapplication.repository.UserRepository
import kotlinx.coroutines.*
import develpment.myapplication.utils.DataContainers

class UserViewModel constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null
    var done = MutableLiveData<Boolean>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun registerUser(user: UserSignupInput) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = userRepository.registerUser(user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        println("------------------------- Funciona")
                        loading.value = false
                        done.postValue(true)
                    } else {
                        done.postValue(false)
                        println("Error : ${response.message()}")
                        onError("Error : ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                done.postValue(false)
                withContext(Dispatchers.Main) {
                    println("Exception : ${e.localizedMessage}")
                    onError("Exception: ${e.localizedMessage}")
                }
            }
        }
    }

    fun updateUser(user: UserChangePassword) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = userRepository.updateUser(user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        loading.value = false
                        done.postValue(true)
                    } else {
                        done.postValue(false)
                        onError("Error : ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                done.postValue(false)
                withContext(Dispatchers.Main) {
                    onError("Exception: ${e.localizedMessage}")
                }
            }
        }
    }

    fun getUserData(email: String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = userRepository.getUserData(EmailUser(email))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        DataContainers.user = (response.body() as UserLoginResult)
                        done.postValue(true)
                        loading.value = false
                    } else {
                        done.postValue(false)
                        onError("Error : ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                done.postValue(false)
                withContext(Dispatchers.Main) {
                    onError("Exception: ${e.localizedMessage}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
