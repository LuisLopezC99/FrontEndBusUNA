package develpment.myapplication.repository

import develpment.myapplication.model.EmailUser
import develpment.myapplication.model.UserSignupInput
import develpment.myapplication.model.UserChangePassword
import develpment.myapplication.service.UserService

class UserRepository constructor(
    private val userService: UserService
) {

    suspend fun registerUser(user: UserSignupInput) = userService.registerUser(user)

    suspend fun getUserData(email: EmailUser) = userService.getUserData(email)

    suspend fun updateUser(user: UserChangePassword) = userService.updateUser(user)

}