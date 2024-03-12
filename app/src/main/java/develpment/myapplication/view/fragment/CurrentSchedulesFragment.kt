package develpment.myapplication.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import develpment.myapplication.R
import develpment.myapplication.databinding.FragmentCurrentSchedulesBinding
import develpment.myapplication.model.ScheduleDTOAndSeatsLeft
import develpment.myapplication.utils.DataContainers


class CurrentSchedulesFragment : Fragment() {

    private var _binding: FragmentCurrentSchedulesBinding? = null

    val binding get() = _binding!!

    private var scheduleList : List<ScheduleDTOAndSeatsLeft> = DataContainers.schedulesDriver as List<ScheduleDTOAndSeatsLeft>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentSchedulesBinding.inflate(inflater, container, false)

        val recyclerView : RecyclerView = binding.root.findViewById(R.id.recycler_view_current_schedules)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CurrentSchedulesAdapter(scheduleList)

        return binding.root
    }

}

class CurrentSchedulesAdapter(private var schedules: List<ScheduleDTOAndSeatsLeft>) : RecyclerView.Adapter<CurrentSchedulesAdapter.ViewHolder>() {

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