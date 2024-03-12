package develpment.myapplication.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import develpment.myapplication.R
import develpment.myapplication.model.ReservationResponse
import develpment.myapplication.repository.ScheduleRepository
import develpment.myapplication.service.ScheduleService
import develpment.myapplication.utils.MyApplication
import develpment.myapplication.viewmodel.MyReservationViewModel
import develpment.myapplication.viewmodel.MyReservationViewModelFactory
import develpment.myapplication.viewmodel.ScheduleViewModel
import develpment.myapplication.viewmodel.ScheduleViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReservationCardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CancelReservationFragment : Fragment() {

    private lateinit var viewModel: MyReservationViewModel
    private val args: CancelReservationFragmentArgs by navArgs()
    var isFinished = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_cancel_reservation, container, false)


        //get arguments
        val reservationId = args.reservationId
        val reservationStatus = args.reservationStatus

        isFinished = reservationStatus == "Finalizado"

        //get viewModel from Provider
        val reservationRepository = (requireActivity().application as MyApplication).reservationRepository

        val viewModelFactory = MyReservationViewModelFactory(reservationRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MyReservationViewModel::class.java)

        viewModel.done.observe(viewLifecycleOwner){
            if(it){
                val scheduleService = ScheduleService.getInstance()
                val scheduleRepository = ScheduleRepository(scheduleService)
                val scheduleViewModel = ViewModelProvider(this, ScheduleViewModelFactory(scheduleRepository))[ScheduleViewModel::class.java]
                scheduleViewModel.getScheduleFilterOnlyDay()
                //nav to other fragment
                scheduleViewModel.done.observe(viewLifecycleOwner){
                    if(it){
                        navigateToMyReservationsFragment()
                    }
                }
            }
        }

        // Fetch reservation details
        val reservation = viewModel.getReservationById(reservationId.toLong()).observe(viewLifecycleOwner) { reservation ->
            // Update RecyclerView
            Log.d("Reservation", reservation.toString())
            // Array de datos para cada elemento
            val layoutIds = arrayOf(
                R.id.userName,
                R.id.education,
                R.id.route,
                R.id.date,
                R.id.time,
                R.id.queue,
                R.id.status
            )

            val icons = arrayOf(
                R.drawable.user_ic,
                R.drawable.education_ic,
                R.drawable.route_ic,
                R.drawable.calendar,
                R.drawable.time_ic,
                R.drawable.queue_ic,
                R.drawable.status_ic
            )

            val labels =
                arrayOf("Usuario:", "Carrera:", "Ruta:", "Fecha:", "Hora:", "En cola:", "Estado")
            val values = arrayOf(
                reservation.userId.firstName + " " + reservation.userId.lastName,
                reservation.userId.carreer,
                reservation.scheduleId.route?.name,
                reservation.scheduleId.day,
                reservation.scheduleId.time,
                "No",
                reservationStatus
            )

            for (i in 0..6) {
                val includedLayout: View = view.findViewById(layoutIds[i])
                //define icon
                val iconView: ImageView = includedLayout.findViewById(R.id.icon)
                iconView.setImageResource(icons[i])
                //define label
                val labelView: TextView = includedLayout.findViewById(R.id.name)
                labelView.text = labels[i]
                //define value
                val valueView: TextView = includedLayout.findViewById(R.id.value)
                valueView.text = values[i]
            }
        }

        val cancelBtn: Button = view.findViewById(R.id.cancel_button)
        cancelBtn.text = if (isFinished) "Finalizado" else "Cancelar"
        cancelBtn.setEnabled(!isFinished)
        cancelBtn.setOnClickListener{
            //cancel reservation
            viewModel.removeReservationById(reservationId.toLong())
        }


        // Devuelve la vista inflada
        return view
    }

    //navigate to MyReservationsFragment
    fun navigateToMyReservationsFragment(){
        val action = CancelReservationFragmentDirections.actionCancelReservationFragmentToReservation()
        findNavController().navigate(action)
    }

}