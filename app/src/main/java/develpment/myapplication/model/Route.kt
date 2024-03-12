data class RouteRequest(
    var name: String
)
data class RouteResponse(
    var id: Long? = null,
    var name: String? = null
)