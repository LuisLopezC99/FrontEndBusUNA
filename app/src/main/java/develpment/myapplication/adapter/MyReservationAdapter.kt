package develpment.myapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import develpment.myapplication.R
import develpment.myapplication.model.OnItemClickListenerTemplate
import develpment.myapplication.model.ReservationResponse
import java.time.LocalDate
import java.time.LocalTime
import java.time.Year
import java.time.format.DateTimeFormatter

class MyReservationAdapter ( var reservations: List<ReservationResponse>, private val itemClickListenerTemplate: OnItemClickListenerTemplate<ReservationResponse>) : RecyclerView.Adapter<MyReservationAdapter.MyReservationHolder>() {
    class MyReservationHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val reservation_date: TextView = view.findViewById(R.id.date_reservation_mr)
        val reservation_time: TextView = view.findViewById(R.id.reservation_time_mr)
        val reservation_route: TextView = view.findViewById(R.id.routename_mr)
        val reservation_campos: TextView = view.findViewById(R.id.campos_mr)
        val reservation_status: Chip = view.findViewById(R.id.estado_chip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReservationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_reservation_item, parent, false)
        return MyReservationHolder(view)
    }

    override fun getItemCount() = reservations.size

    override fun onBindViewHolder(holder: MyReservationHolder, position: Int) {
        val reservation = reservations[position]
        val hoy = LocalDate.now()
        val formato = DateTimeFormatter.ofPattern("dd/MM")
        val fechaActual = hoy.format(formato)

        val hora = LocalTime.now()
        val formatoHora = DateTimeFormatter.ofPattern("hh:mm a")
        val horaActual = hora.format(formatoHora)
        var fechaResult = "Not valid"
        var horaResult = "Not valid"


        Log.d("Reservation Object", reservation.toString())

        reservation.scheduleId?.let { schedule_id ->

            schedule_id.route?.let { route ->
                holder.reservation_route.text = route.name
            }
            schedule_id.bus?.let { bus ->
                holder.reservation_campos.text = "Campos: ${bus.seats}"
            }
            schedule_id.day?.let { day ->
                holder.reservation_date.text = day
                fechaResult = this.compareDates(fechaActual, day)
            }
            schedule_id.time?.let { time ->
                holder.reservation_time.text = time
                horaResult = this.compareHours(horaActual, time)
            }



            if (fechaResult == "Today") {
                holder.reservation_status.setChipBackgroundColorResource(R.color.rojo)
                holder.reservation_status.text = "Finalizado"
            } else if (fechaResult == "Reservation") {
                holder.reservation_status.setChipBackgroundColorResource(R.color.verde)
                holder.reservation_status.text = "Pendiente"
            } else if (fechaResult == "Equal" && horaResult == "Today") {
                holder.reservation_status.setChipBackgroundColorResource(R.color.verde)
                holder.reservation_status.text = "Pendiente"
            } else if (fechaResult == "Equal" && horaResult == "Reservation") {
                holder.reservation_status.setChipBackgroundColorResource(R.color.rojo)
            }else if (fechaResult == "Not valid" && horaResult == "Not valid") {
                holder.reservation_status.setChipBackgroundColorResource(R.color.colorFocused)
                holder.reservation_status.text = "Finalizado"
            }

            holder.itemView.setOnClickListener {
                itemClickListenerTemplate.onItemClick(it, position, reservation)
            }
        }

    }

    fun setData(reservations: List<ReservationResponse>) {
        this.reservations = reservations
        notifyDataSetChanged()
    }
    fun compareDates(todayDate: String, resDate: String): String {
        val currentYear = Year.now().value.toString()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date1 = LocalDate.parse("$todayDate/$currentYear", formatter)
        val date2 = LocalDate.parse("$resDate/$currentYear", formatter)
        return if (date1.isAfter(date2)) {
            "Today" //retorna today si la fecha de hoy es mayor a la fecha de la reserva
        } else if (date1.isBefore(date2)) {
            "Reservation" //retorna reservation si la fecha de hoy es menor a la fecha de la reserva
        } else {
            "Equal" //retorna equal si la fecha de hoy es igual a la fecha de la reserva
        }
    }

    //fun to compare hours
    fun compareHours(todayHour: String, resHour: String): String {
        val formato = DateTimeFormatter.ofPattern("h:mm a") // Formato sin ceros iniciales

        val hora1 = LocalTime.parse(todayHour, formato)
        val hora2 = LocalTime.parse(resHour, formato)

        return when {
            hora1.isAfter(hora2) -> "Today"
            hora1.isBefore(hora2) -> "Reservation"
            else -> "Equal"
        }
    }


}