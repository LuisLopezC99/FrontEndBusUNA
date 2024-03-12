package develpment.myapplication.service
import develpment.myapplication.model.ReservationResponse
import develpment.myapplication.model.ReservationRequest
import retrofit2.Response
import retrofit2.http.*
interface ReservationService {
    @GET("/v1/reservations")
    suspend fun getAllReservations(): Response<List<ReservationResponse>>
    @GET("/v1/reservations/{id}")
    suspend fun getReservationById(@Path("id") id: Long) : Response<ReservationResponse>

    @GET("/v1/reservations/user/{userId}")
    suspend fun getReservationByUserId(@Path("userId") id: Long) : Response<List<ReservationResponse>>

    @DELETE("/v1/reservations/{id}")
    suspend fun deleteReservationById(@Path("id") id: Long): Response<Void>

    @POST("/v1/reservations")
    suspend fun createReservation(@Body reservationRequest: ReservationRequest) : Response<ReservationResponse>

    @PUT("/v1/reservations")
    suspend fun updateReservation(@Body reservationRequest: ReservationRequest) : Response<ReservationResponse>


    /*
     * Function or any member of the class that can be called without having the instance of the
     * class then you can write the same as a member of a companion object inside the class
     */
    companion object{
        private var reservationService : ReservationService? = null
        fun getInstance() : ReservationService {
            if (reservationService == null) {
                reservationService = ServiceBuilder.buildService(ReservationService::class.java)
            }
            return reservationService!!
        }
    }
}