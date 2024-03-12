package develpment.myapplication.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import develpment.myapplication.DialogFragment
import develpment.myapplication.R
import develpment.myapplication.databinding.FragmentFormSearchScheduleBinding
import develpment.myapplication.model.IdSchedule
import develpment.myapplication.model.IdUser
import develpment.myapplication.model.OnItemClickListenerTemplate
import develpment.myapplication.model.ReservationRequest
import develpment.myapplication.model.ScheduleDTOAndSeatsLeft
import develpment.myapplication.model.ScheduleDriverFiltered
import develpment.myapplication.model.ScheduleFilterOnlyDay
import develpment.myapplication.repository.ReservationRepository
import develpment.myapplication.repository.ScheduleRepository
import develpment.myapplication.service.ReservationService
import develpment.myapplication.service.ScheduleService
import develpment.myapplication.utils.DataContainers
import develpment.myapplication.viewmodel.MyReservationViewModel
import develpment.myapplication.viewmodel.MyReservationViewModelFactory
import develpment.myapplication.viewmodel.ReservationViewModel
import develpment.myapplication.viewmodel.ScheduleViewModel
import develpment.myapplication.viewmodel.ScheduleViewModelFactory
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter


class FormSearchScheduleFragment : Fragment() , OnItemClickListenerTemplate <ScheduleDTOAndSeatsLeft> {

    private var _binding: FragmentFormSearchScheduleBinding? = null

    private val binding get() = _binding!!

    private var scheduleList : List<ScheduleDTOAndSeatsLeft> = DataContainers.schedulesStudents as List<ScheduleDTOAndSeatsLeft>

    var adapter: FragmentFormSearchScheduleAdapter = FragmentFormSearchScheduleAdapter(scheduleList,this)

    private var month = 0

    private var dayOfMonth = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFormSearchScheduleBinding.inflate(inflater, container, false)

        val recyclerView: RecyclerView = binding.root.findViewById(R.id.recycler_view_current_schedules_dates)

        val scheduleService = ScheduleService.getInstance()
        val scheduleRepository = ScheduleRepository(scheduleService)
        val scheduleViewModel = ViewModelProvider(this, ScheduleViewModelFactory(scheduleRepository))[ScheduleViewModel::class.java]

        scheduleViewModel.done.observe(viewLifecycleOwner){
            if(it){
                scheduleList = DataContainers.schedulesStudents as List<ScheduleDTOAndSeatsLeft>

                // Actualiza el adaptador con la nueva lista
                adapter.updateData(scheduleList)

                // Notifica al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged()
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        //adapter = FragmentFormSearchScheduleAdapter(scheduleList,this)
        recyclerView.adapter = adapter

        binding.checkBoxSchedule.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if(isChecked){
                println("Checked")
                binding.calendarOptionsSchedule.visibility = View.VISIBLE
            }else{
                binding.calendarOptionsSchedule.visibility = View.GONE
                println("Unchecked")
            }
        }

        binding.calendarOptionsSchedule.setOnDateChangeListener {
                view, year, month, dayOfMonth ->
            println("Date changed")
            println("Year: $year, Month: ${month+1}, Day: $dayOfMonth")
            this.month = month
            this.dayOfMonth = dayOfMonth
            scheduleViewModel.getScheduleFilterOnlyDay( ScheduleFilterOnlyDay("$dayOfMonth/${month+1}"))
        }

        scheduleViewModel.getScheduleFilterOnlyDay()

        return binding.root
    }

    override fun onItemClick(view: View, position: Int, dato: ScheduleDTOAndSeatsLeft) {
        if (dato.seatsLeft < 1){
            Toast.makeText(context, "No hay cupos disponibles", Toast.LENGTH_SHORT).show()
            return
        }

        val hoy = LocalDate.now()
        val formato = DateTimeFormatter.ofPattern("dd/MM")
        val fechaActual = hoy.format(formato)

        val currentYear = Year.now().value.toString()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date1 = LocalDate.parse("$fechaActual/$currentYear", formatter)
        val date2 = LocalDate.parse("${dato.scheduleDTO.day}/$currentYear", formatter)
        if (date1.isAfter(date2)) {
            Toast.makeText(context, "No se puede reservar un viaje en el pasado", Toast.LENGTH_SHORT).show()
            return
        }

        // En tu actividad o fragmento
        val customDialog = DialogFragment(requireContext()) {
            var print = ReservationRequest(IdUser(DataContainers.user!!.id), IdSchedule(dato.scheduleDTO.id))
            var reservationService = ReservationService.getInstance()
            var reservationRepository = ReservationRepository(reservationService)
            var reservationViewModel = ViewModelProvider(this, MyReservationViewModelFactory(reservationRepository))[MyReservationViewModel::class.java]

            reservationViewModel.addReservation(print).observe(viewLifecycleOwner){
                    reservationF -> println("Reservation: $reservationF")
                val scheduleService = ScheduleService.getInstance()
                val scheduleRepository = ScheduleRepository(scheduleService)
                val scheduleViewModel = ViewModelProvider(this, ScheduleViewModelFactory(scheduleRepository))[ScheduleViewModel::class.java]
                scheduleViewModel.getScheduleFilterOnlyDay( ScheduleFilterOnlyDay("$dayOfMonth/${month+1}"))
            }
        }

        customDialog.show()



    }

}

class FragmentFormSearchScheduleAdapter(private var schedules: List<ScheduleDTOAndSeatsLeft>, private val itemClickListenerTemplate: OnItemClickListenerTemplate<ScheduleDTOAndSeatsLeft>) : RecyclerView.Adapter<FragmentFormSearchScheduleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ruta = itemView.findViewById<TextView>(R.id.routename_mr)
        val hora = itemView.findViewById<TextView>(R.id.reservation_time_mr)
        val fecha = itemView.findViewById<TextView>(R.id.date_reservation_mr)
        val campos = itemView.findViewById<TextView>(R.id.campos_mr)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_reservation_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = schedules[position]
        holder.ruta.text = schedule.scheduleDTO.route.name
        holder.hora.text = schedule.scheduleDTO.time
        holder.fecha.text = schedule.scheduleDTO.day
        holder.campos.text = schedule.seatsLeft.toString()

        holder.itemView.setOnClickListener {
            itemClickListenerTemplate.onItemClick(it, position, schedule)
        }
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun updateData(newSchedules: List<ScheduleDTOAndSeatsLeft>) {
        schedules = newSchedules
        notifyDataSetChanged()
    }

}