package develpment.myapplication.viewmodel

import BusName
import BusResponse
import RouteResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import develpment.myapplication.model.ReservationRequest
import develpment.myapplication.model.ReservationResponse
import develpment.myapplication.model.ScheduleResponse
import develpment.myapplication.model.User
import develpment.myapplication.repository.ReservationRepository
import kotlinx.coroutines.launch
import java.util.Date

class MyReservationViewModel(private val reservationRepository: ReservationRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _reservations = MutableLiveData<List<ReservationResponse>>()
    val reservations: LiveData<List<ReservationResponse>> get() = _reservations

    // make done
    private val _done = MutableLiveData<Boolean>()
    val done: LiveData<Boolean> get() = _done



    //create update reservation by id
    fun updateReservationById(id: Long, reservation: ReservationRequest) {
        viewModelScope.launch {
            try {
                val result = reservationRepository.updateReservation(reservation)
                if (result.isSuccessful) {
                    val currentList = _reservations.value.orEmpty().toMutableList()
                    val index = currentList.indexOfFirst { it.id == id }
                    currentList[index] = result.body()!!
                    _reservations.value = currentList
                } else {
                    throw Exception("Error al actualizar la reserva")
                }
            }catch (e: Exception){
                println("Ocurrió un error: ${e.message}")
            }
        }
    }


    //Add others methods to add, remove or update ReservationsResponse objects
    fun addReservation(reservation: ReservationRequest): LiveData<ReservationResponse> {
        val reservation1 = MutableLiveData<ReservationResponse>()
        viewModelScope.launch {
            val result = reservationRepository.createReservation(reservation)
            if (result.isSuccessful) {
                val currentList = _reservations.value.orEmpty().toMutableList()
                currentList.add(result.body()!!)
                _reservations.value = currentList
                reservation1.value = result.body()
            } else {
                println("Ocurrió un error: ${result.code()}")
            }
        }
        return reservation1
    }

    //create a method to get all reservations by user id
    fun getReservationByUserId(id: Long): LiveData<List<ReservationResponse>> {
        val reservation = MutableLiveData<List<ReservationResponse>>()
        viewModelScope.launch {
            val result = reservationRepository.getReservationByUserId(id)
            if (result.isSuccessful) {
                reservation.value = result.body()
            } else {
                //handle the error
                println("Ocurrió un error: ${result.code()}")
            }
        }
        return reservation
    }


    //create a method to get a reservation by id
    fun getReservationById(id: Long): LiveData<ReservationResponse> {
        val reservation = MutableLiveData<ReservationResponse>()
        viewModelScope.launch {
            val result = reservationRepository.getReservationById(id)
            if (result.isSuccessful) {
                reservation.value = result.body()
            } else {
                //handle the error
                println("Ocurrió un error: ${result.code()}")
            }
        }
        return reservation
    }

//create remove reservation by id
    fun removeReservationById(id: Long) {
        viewModelScope.launch {
            val result = reservationRepository.deleteReservationById(id)
            if (result.isSuccessful) {
                val currentList = _reservations.value.orEmpty().toMutableList()
                currentList.removeIf { it.id == id }
                _reservations.value = currentList
                _done.postValue(true)
            } else {
                _done.postValue(false)
                println("Ocurrió un error: ${result.code()}")
            }
        }
    }



}
