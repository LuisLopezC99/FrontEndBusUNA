package develpment.myapplication.service


import develpment.myapplication.model.EmailUser
import develpment.myapplication.model.UserSignupInput
import develpment.myapplication.model.UserChangePassword
import develpment.myapplication.model.UserLoginResult

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService{

    @POST("/v1/registerUser")
    suspend fun registerUser(@Body user: UserSignupInput) : Response<UserLoginResult>

    @POST("/v1/userData")
    suspend fun getUserData(@Body email: EmailUser) : Response<UserLoginResult>

    @PUT("/v1/updateUser")
    suspend fun updateUser(@Body user: UserChangePassword) : Response<UserLoginResult>

    companion object{
        var userService: UserService? = null

        fun getInstance(): UserService {
            if (userService == null) {
                userService = ServiceBuilder.buildService(UserService::class.java)

            }
            return userService!!
        }
    }
}