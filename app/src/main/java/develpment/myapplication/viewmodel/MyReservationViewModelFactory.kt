package develpment.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import develpment.myapplication.repository.ReservationRepository

class MyReservationViewModelFactory(private val reservationRepository: ReservationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyReservationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyReservationViewModel(reservationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
