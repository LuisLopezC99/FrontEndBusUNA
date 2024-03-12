package develpment.myapplication.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import develpment.myapplication.R
import develpment.myapplication.viewmodel.MyReservationViewModel
import develpment.myapplication.adapter.MyReservationAdapter
import develpment.myapplication.model.OnItemClickListenerTemplate
import develpment.myapplication.model.ReservationResponse
import develpment.myapplication.utils.DataContainers
import develpment.myapplication.utils.MyApplication
import develpment.myapplication.viewmodel.MyReservationViewModelFactory
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MyReservationsFragment : Fragment(), OnItemClickListenerTemplate<ReservationResponse> {
    private lateinit var viewModel: MyReservationViewModel
    private lateinit var adapter: MyReservationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_reservations, container, false)

        // Set up RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context) // Use view.context instead of context

        // Get ViewModel from Provider
        val reservationRepository = (requireActivity().application as MyApplication).reservationRepository
        val viewModelFactory = MyReservationViewModelFactory(reservationRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MyReservationViewModel::class.java)

        // Observe data in ViewModel
        viewModel.getReservationByUserId(DataContainers.user!!.id).observe(viewLifecycleOwner) { reservations ->
            adapter = MyReservationAdapter(reservations, this)
            recyclerView.adapter = adapter
        }

        viewModel.reservations.observe(viewLifecycleOwner) { reservations ->
            // Update RecyclerView
            adapter.setData(reservations)
        }

        //set up the status of the reservation

        return view
    }


    override fun onItemClick(view: View, position: Int, dato: ReservationResponse) {
       //create action to go to CancelReservationFragment
        val action = MyReservationsFragmentDirections.actionReservationToCancelReservationFragment(
            dato.id.toString(),
            view.findViewById<Chip>(R.id.estado_chip).text.toString()
        )
        findNavController().navigate(action)
    }

}