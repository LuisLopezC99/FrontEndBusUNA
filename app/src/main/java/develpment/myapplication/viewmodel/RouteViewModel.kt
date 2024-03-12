package develpment.myapplication.viewmodel
import RouteResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RouteViewModel : ViewModel()  {
    val route = MutableLiveData<RouteResponse>()
    val routeList = MutableLiveData<List<RouteResponse>>()
    //Get Route
    fun getRoute() {
        //**logic here to get the route**//
        //_route = Provider.getRouteById(id)
        //route.postValue(_route)
    }
    //Get Route List
    fun getRouteList() {
        //**logic here to get the route list**//
        //_routeList = Provider.getRouteList()
        //routeList.postValue(_routeList)
    }
}