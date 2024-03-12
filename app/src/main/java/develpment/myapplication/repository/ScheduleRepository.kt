package develpment.myapplication.repository
import develpment.myapplication.model.ScheduleDriver
import develpment.myapplication.model.ScheduleDriverFiltered
import develpment.myapplication.model.ScheduleFilterOnlyDay
import develpment.myapplication.service.ScheduleService
import develpment.myapplication.model.ScheduleRequest
class ScheduleRepository  constructor(
    private val scheduleService: ScheduleService
){
    suspend fun getAllSchedules() = scheduleService.getAllSchedules()
    suspend fun getScheduleById(id : Long) = scheduleService.getScheduleById(id)
    suspend fun deleteScheduleById(id : Long) = scheduleService.deleteScheduleById(id)
    suspend fun createSchedule(scheduleRequest: ScheduleRequest) = scheduleService.createSchedule(scheduleRequest)
    suspend fun updateSchedule(scheduleRequest: ScheduleRequest) = scheduleService.updateSchedule(scheduleRequest)

    suspend fun getScheduleDriverFiltered(scheduleDriverFiltered: ScheduleDriverFiltered) = scheduleService.getScheduleDriverFiltered(scheduleDriverFiltered)

    suspend fun getScheduleDriver(scheduleDriver: ScheduleDriver) = scheduleService.getScheduleDriver(scheduleDriver)

    suspend fun getScheduleFilterOnlyDay(scheduleFilterOnlyDay: ScheduleFilterOnlyDay) = scheduleService.getScheduleFilterOnlyDay(scheduleFilterOnlyDay)

    suspend fun getScheduleFilterOnlyDay() = scheduleService.getScheduleFilterOnlyDay()
}