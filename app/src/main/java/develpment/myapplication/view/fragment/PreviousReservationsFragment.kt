package develpment.myapplication.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import develpment.myapplication.R

class PreviousReservationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_previous_reservations, container, false)

        //val btnReserveSchedule = root.findViewById<ImageView>(R.id.btnReserveSchedule)
        //btnReserveSchedule.setOnClickListener{
        //findNavController().navigate(PreviousReservationsFragmentDirections.actionPreviousReservationsFragment3ToFormSearchScheduleFragment())
        //}

        return root

    }
}
