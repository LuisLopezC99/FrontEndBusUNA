package develpment.myapplication.model


/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)

data class LoginRequest(
    var username: String,
    var password: String,
)

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val username: String,
    val authorities: List<Authority>,
    //... other data fields that may be accessible to the UI
) {
}

data class Role(
    var id: Long,
    var name: String,
    var userList: List<User>
)
data class UserLoginResult(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val enabled: Boolean,
    var roles: Collection<Role>? = null,
    val carreer: String?,
)

data class EmailUser(
    var email: String = "",
)
data class IdUser(
    var id: Long? = null,
)

data class UserChangePassword(
    var email: String = "",
    var password: String = "",
)

data class UserLoginResponse(
    var username: String,
    var password: String,
    var authorities: List<Authority>,
    var accountNonExpired: Boolean,
    var accountNonLocked: Boolean,
    var credentialsNonExpired: Boolean,
    var enabled: Boolean,
)

data class Authority(
    var authority: String,
)

data class UserSignupInput(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var carreer: String,
    var role: String?,
)
data class User(
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var enabled: Boolean = false
)

data class UserResponse(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var carreer: String = "",
    var role: String = "",
)