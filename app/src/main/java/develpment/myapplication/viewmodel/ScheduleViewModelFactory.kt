package develpment.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import develpment.myapplication.repository.ScheduleRepository

@Suppress("UNCHECKED_CAST")
class ScheduleViewModelFactory constructor(
    private val repository: ScheduleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            ScheduleViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}