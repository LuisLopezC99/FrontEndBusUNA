package develpment.myapplication.service

import develpment.myapplication.model.LoginRequest
import develpment.myapplication.model.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/v1/users/login")
    suspend fun login(@Body userLogin: LoginRequest) : Response<UserLoginResponse>

    companion object {
        private var loginService : LoginService? = null
        fun getInstance() : LoginService {
            if (loginService == null) {
                loginService = ServiceBuilder.buildService(LoginService::class.java)
            }
            return loginService!!
        }
    }
}