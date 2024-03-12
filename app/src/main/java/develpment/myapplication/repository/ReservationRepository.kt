package develpment.myapplication.repository
import develpment.myapplication.service.ReservationService
import develpment.myapplication.model.ReservationRequest
class ReservationRepository constructor(
    private val reservationService: ReservationService
){
    suspend fun getAllReservations() = reservationService.getAllReservations()
    suspend fun getReservationById(id : Long) = reservationService.getReservationById(id)
    suspend fun getReservationByUserId(id : Long) = reservationService.getReservationByUserId(id)
    suspend fun deleteReservationById(id : Long) = reservationService.deleteReservationById(id)
    suspend fun createReservation(reservationRequest: ReservationRequest) = reservationService.createReservation(reservationRequest)
    suspend fun updateReservation(reservationRequest: ReservationRequest) = reservationService.updateReservation(reservationRequest)

}