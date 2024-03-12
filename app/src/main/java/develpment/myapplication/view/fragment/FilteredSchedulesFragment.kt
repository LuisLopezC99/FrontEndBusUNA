package develpment.myapplication.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import develpment.myapplication.R
import develpment.myapplication.databinding.FragmentFilteredSchedulesBinding
import develpment.myapplication.model.ScheduleDTOAndSeatsLeft
import develpment.myapplication.model.ScheduleDriverFiltered
import develpment.myapplication.repository.ScheduleRepository
import develpment.myapplication.service.ScheduleService
import develpment.myapplication.utils.DataContainers
import develpment.myapplication.viewmodel.ScheduleViewModel
import develpment.myapplication.viewmodel.ScheduleViewModelFactory


class FilteredSchedulesFragment : Fragment() {

    private var _binding: FragmentFilteredSchedulesBinding? = null
    private val binding get() = _binding!!

    private var scheduleList : List<ScheduleDTOAndSeatsLeft> = DataContainers.schedulesDriver as List<ScheduleDTOAndSeatsLeft>

    private lateinit var adapter: FilteredSchedulesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilteredSchedulesBinding.inflate(inflater, container, false)

        val recyclerView: RecyclerView = binding.root.findViewById(R.id.recycler_view_current_schedules)

        val scheduleService = ScheduleService.getInstance()
        val scheduleRepository = ScheduleRepository(scheduleService)
        val scheduleViewModel = ViewModelProvider(this, ScheduleViewModelFactory(scheduleRepository))[ScheduleViewModel::class.java]

        scheduleViewModel.done.observe(viewLifecycleOwner){
            if(it){
                scheduleList = DataContainers.schedulesDriver as List<ScheduleDTOAndSeatsLeft>

                // Actualiza el adaptador con la nueva lista
                adapter.updateData(scheduleList)

                // Notifica al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged()
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FilteredSchedulesAdapter(scheduleList)
        recyclerView.adapter = adapter

        binding.checkBox.setOnCheckedChangeListener{
            buttonView, isChecked ->
            if(isChecked){
                println("Checked")
                binding.calendarOptions.visibility = View.VISIBLE
            }else{
                binding.calendarOptions.visibility = View.GONE
                println("Unchecked")
            }
        }

        binding.calendarOptions.setOnDateChangeListener {
            view, year, month, dayOfMonth ->
            println("Date changed")
            println("Year: $year, Month: ${month+1}, Day: $dayOfMonth")

            scheduleViewModel.getScheduleDriverFiltered(ScheduleDriverFiltered(DataContainers.user!!.id.toString(), "$dayOfMonth/${month+1}"))
        }

        return binding.root
    }

}

class FilteredSchedulesAdapter(private var schedules: List<ScheduleDTOAndSeatsLeft>) : RecyclerView.Adapter<FilteredSchedulesAdapter.ViewHolder>() {

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