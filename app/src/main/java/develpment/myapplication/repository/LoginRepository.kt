package develpment.myapplication.repository


import develpment.myapplication.model.LoginRequest
import develpment.myapplication.model.UserLoginResponse
import develpment.myapplication.service.LoginService
import develpment.myapplication.utils.MyApplication.Companion.sessionManager
import retrofit2.Response

class LoginRepository constructor (
    private val loginService: LoginService
){

    // in-memory cache of the loggedInUser object
    private var user: UserLoginResponse? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        sessionManager?.deleteAuthToken()
    }

    suspend fun login(userLogin: LoginRequest)  : Response<UserLoginResponse> {
        val response = loginService.login(userLogin)

        if (response.isSuccessful) {
            setLoggedInUser(response.body(), response.headers()["Authorization"].toString())
        }

        return response
    }

    private fun setLoggedInUser(loginRequest: UserLoginResponse?, token:String) {
        this.user = loginRequest
        sessionManager?.saveAuthToken(token)

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}