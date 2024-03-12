package develpment.myapplication.model

import BusName
import BusResponse
import RouteResponse
import java.util.Date

data class ScheduleRequest(
    var routeId: Long,
    var busId: Long,
    var day: Date,
    var time: Date
)

data class ScheduleResponse (
    var id: Long? = null,
    var route: RouteResponse,
    var busName: BusName,
    var day: Date,
    var time: Date
)

//create a scheduleTest class with the same attributes as ScheduleResponse
data class ScheduleRes (
    var id: Long? = null,
    var day: String,
    var time: String,
    var bus: BusName,
    var route: RouteResponse
)

data class ScheduleDriverFiltered(
    val userId: String,
    val day: String
)

data class ScheduleDriver(
    val userId: String
)

data class ScheduleDTOAndSeatsLeft(
    val scheduleDTO: ScheduleDTO,
    val seatsLeft: Int
)

data class ScheduleDTO(
    val id: Long?,
    val route: RouteResponse,
    val bus: BusName,
    val day: String,
    val time: String
)

data class ScheduleFilterOnlyDay(
    val day: String
)

data class IdSchedule(
    val id: Long? = null
)