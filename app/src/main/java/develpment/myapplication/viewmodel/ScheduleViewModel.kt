package develpment.myapplication.viewmodel
import android.provider.ContactsContract.CommonDataKinds.Contactables
import android.provider.ContactsContract.Data
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import develpment.myapplication.model.ScheduleDriver
import develpment.myapplication.model.ScheduleDriverFiltered
import develpment.myapplication.model.ScheduleFilterOnlyDay
import develpment.myapplication.model.ScheduleResponse
import develpment.myapplication.repository.ScheduleRepository
import develpment.myapplication.utils.DataContainers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleViewModel constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null
    var done = MutableLiveData<Boolean>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getScheduleDriverFiltered(scheduleDriverFiltered: ScheduleDriverFiltered) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = scheduleRepository.getScheduleDriverFiltered(scheduleDriverFiltered)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        loading.value = false
                        DataContainers.schedulesDriver = response.body()!!
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

    fun getScheduleDriver(scheduleDriver: ScheduleDriver) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = scheduleRepository.getScheduleDriver(scheduleDriver)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        DataContainers.schedulesDriver = response.body()!!
                        println("Schedules: ${DataContainers.schedulesDriver}")
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

    fun getScheduleFilterOnlyDay(scheduleFilterOnlyDay: ScheduleFilterOnlyDay) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = scheduleRepository.getScheduleFilterOnlyDay(scheduleFilterOnlyDay)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        DataContainers.schedulesStudents = response.body()!!

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

    fun getScheduleFilterOnlyDay() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = scheduleRepository.getScheduleFilterOnlyDay()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        DataContainers.schedulesStudents = response.body()!!
                        println(DataContainers.schedulesStudents)
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



    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}