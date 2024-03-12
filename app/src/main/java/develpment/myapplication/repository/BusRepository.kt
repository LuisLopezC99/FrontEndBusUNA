package develpment.myapplication.repository
import BusRequest
import develpment.myapplication.service.BusService
class BusRepository constructor(
    private val busService: BusService
){
    suspend fun getAllBuss() = busService.getAllBuses()
    suspend fun getBusById(id : Long) = busService.getBusById(id)
    suspend fun deleteBusById(id : Long) = busService.deleteBusById(id)
    suspend fun createBus(busRequest: BusRequest) = busService.createBus(busRequest)
    suspend fun updateBus(busRequest: BusRequest) = busService.updateBus(busRequest)
}