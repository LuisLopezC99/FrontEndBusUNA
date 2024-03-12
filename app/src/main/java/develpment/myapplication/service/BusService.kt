package develpment.myapplication.service
import BusRequest
import BusResponse
import retrofit2.Response
import retrofit2.http.*
interface BusService {

    @GET("/v1/bus")
    suspend fun getAllBuses(): Response<List<BusResponse>>

    @GET("/v1/bus/{id}")
    suspend fun getBusById(@Path("id") id: Long) : Response<BusResponse>

    @DELETE("/v1/bus/{id}")
    suspend fun deleteBusById(@Path("id") id: Long): Response<Void>

    @POST("/v1/bus")
    suspend fun createBus(@Body busRequest: BusRequest) : Response<BusResponse>

    @PUT("/v1/bus")
    suspend fun updateBus(@Body busRequest: BusRequest) : Response<BusResponse>


    companion object{
        private var busService : BusService? = null
        fun getInstance() : BusService {
            if (busService == null) {
                busService = ServiceBuilder.buildService(BusService::class.java)
            }
            return busService!!
        }
    }
}