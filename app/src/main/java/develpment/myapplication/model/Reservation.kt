package develpment.myapplication.model

import BusName
import RouteResponse
import java.util.Date

data class ReservationRequest(
    var userId: IdUser,
    var scheduleId: IdSchedule
)
data class ReservationResponse(
    var id: Long? = null,
    var userId: UserSignupInput,
    var scheduleId: ScheduleRes
){
    override fun toString(): String {
        return "ReservationResponse(id=$id, user=$userId, schedule=$scheduleId)"
    }
}
