package develpment.myapplication.utils

import develpment.myapplication.model.ScheduleDTOAndSeatsLeft
import develpment.myapplication.model.UserLoginResult

object DataContainers {
    var user: UserLoginResult? = null
    var schedulesDriver: List<ScheduleDTOAndSeatsLeft>? = null
    var schedulesStudents: List<ScheduleDTOAndSeatsLeft>? = null
}



