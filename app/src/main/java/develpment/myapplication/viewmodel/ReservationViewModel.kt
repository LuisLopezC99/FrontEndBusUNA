package develpment.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import develpment.myapplication.model.Provider
import develpment.myapplication.model.ReservationResponse

//Reservation View Model
class ReservationViewModel: ViewModel() {
    val reservation = MutableLiveData<ReservationResponse>()
    val reservationList = MutableLiveData<List<ReservationResponse>>()

    //Get Reservation
//    fun getReservation() {
//        //**logic here to get the reservation**//
//        val _reservation = Provider.findReservationById(1);
//        reservation.postValue(_reservation)
//    }

    //Get Reservation List
//    fun getReservationList() {
//        val test = listOf<Reservation>(
//            //create reservation objects
//            Reservation(1, "Reservation 1", "Notes 1"),
//            Reservation(2, "Reservation 2", "Notes 2"),
//            Reservation(3, "Reservation 3", "Notes 3"),
//        )
//        //**logic here to get the reservation list**//
//        val _reservationList = test;
//        reservationList.postValue(_reservationList)
//    }

}