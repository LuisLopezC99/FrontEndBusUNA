package develpment.myapplication.utils

import android.app.Application
import develpment.myapplication.repository.ReservationRepository
import develpment.myapplication.service.ReservationService

class MyApplication : Application() {

    val reservationRepository: ReservationRepository by lazy {
        ReservationRepository(ReservationService.getInstance())
    }

    override fun onCreate() {
        super.onCreate()
        createSessionManager(SessionManager(applicationContext))
    }

    companion object {
        var sessionManager: SessionManager? = null

        fun createSessionManager(newInstance: SessionManager){
            sessionManager = newInstance
        }
    }
}
