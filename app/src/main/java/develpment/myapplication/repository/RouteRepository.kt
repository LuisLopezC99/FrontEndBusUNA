package develpment.myapplication.repository
import RouteRequest
import develpment.myapplication.service.RouteService

class RouteRepository constructor(
    private val routeService: RouteService
){
    suspend fun getAllRoutes() = routeService.getAllRoutes()
    suspend fun getRouteById(id : Long) = routeService.getRouteById(id)
    suspend fun deleteRouteById(id : Long) = routeService.deleteRouteById(id)
    suspend fun createRoute(routeRequest: RouteRequest) = routeService.createRoute(routeRequest)
    suspend fun updateRoute(routeRequest: RouteRequest) = routeService.updateRoute(routeRequest)
}