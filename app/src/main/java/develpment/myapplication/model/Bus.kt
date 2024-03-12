import develpment.myapplication.model.User

data class BusRequest(
    var seats: Int,
    var userId:Long
)

data class BusResponse (
    var id: Long? = null,
    var seats: Int,
    var user: User
)
data class BusName(
    var id: Long? = null,
    var seats: Int? = null
)