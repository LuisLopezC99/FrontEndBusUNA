package develpment.myapplication.service
import develpment.myapplication.model.ScheduleDTOAndSeatsLeft
import develpment.myapplication.model.ScheduleDriver
import develpment.myapplication.model.ScheduleDriverFiltered
import develpment.myapplication.model.ScheduleFilterOnlyDay
import develpment.myapplication.model.ScheduleRequest
import develpment.myapplication.model.ScheduleResponse
import org.jetbrains.annotations.Async.Schedule
import retrofit2.Response
import retrofit2.http.*
interface ScheduleService {
    @GET("/v1/schedules")
    suspend fun getAllSchedules(): Response<List<ScheduleResponse>>
    @GET("/v1/schedules/{id}")
    suspend fun getScheduleById(@Path("id") id: Long) : Response<ScheduleResponse>

    @DELETE("/v1/schedules/{id}")
    suspend fun deleteScheduleById(@Path("id") id: Long): Response<Void>

    @POST("/v1/schedules")
    suspend fun createSchedule(@Body sheduleRequest: ScheduleRequest) : Response<ScheduleResponse>

    @PUT("/v1/schedules")
    suspend fun updateSchedule(@Body sheduleRequest: ScheduleRequest) : Response<ScheduleResponse>

    @POST("/v1/schedule/filteredDay")
    suspend fun getScheduleDriverFiltered(@Body scheduleDriverFiltered: ScheduleDriverFiltered) : Response<List<ScheduleDTOAndSeatsLeft>>

    @POST("/v1/schedule/currentDay")
    suspend fun getScheduleDriver(@Body scheduleDriver: ScheduleDriver) : Response<List<ScheduleDTOAndSeatsLeft>>

    @POST ("/v1/schedule/filteredDayOnly")
    suspend fun getScheduleFilterOnlyDay(@Body scheduleFilterOnlyDay: ScheduleFilterOnlyDay) : Response<List<ScheduleDTOAndSeatsLeft>>

    @GET("/v1/schedule/filteredDayOnly")
    suspend fun getScheduleFilterOnlyDay() : Response<List<ScheduleDTOAndSeatsLeft>>


    companion object{
        private var scheduleService : ScheduleService? = null
        fun getInstance() : ScheduleService {
            if (scheduleService == null) {
                scheduleService = ServiceBuilder.buildService(ScheduleService::class.java)
            }
            return scheduleService!!
        }
    }
}