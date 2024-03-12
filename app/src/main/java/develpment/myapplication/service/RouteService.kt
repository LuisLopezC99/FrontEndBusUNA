package develpment.myapplication.service
import RouteRequest
import RouteResponse
import retrofit2.Response
import retrofit2.http.*
interface RouteService {
    @GET("/v1/routes")
    suspend fun getAllRoutes(): Response<List<RouteResponse>>

    @GET("/v1/routes/{id}")
    suspend fun getRouteById(@Path("id") id: Long) : Response<RouteResponse>

    @DELETE("/v1/routes/{id}")
    suspend fun deleteRouteById(@Path("id") id: Long): Response<Void>

    @POST("/v1/routes")
    suspend fun createRoute(@Body routeRequest: RouteRequest) : Response<RouteResponse>

    @PUT("/v1/routes")
    suspend fun updateRoute(@Body routeRequest: RouteRequest) : Response<RouteResponse>


    companion object{
        private var routeService : RouteService? = null
        fun getInstance() : RouteService {
            if (routeService == null) {
                routeService = ServiceBuilder.buildService(RouteService::class.java)
            }
            return routeService!!
        }
    }
}