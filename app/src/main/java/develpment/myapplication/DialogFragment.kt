package develpment.myapplication

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView

class DialogFragment(context: Context, private val onConfirm: () -> Unit) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.fragment_dialog)

        val modalMessage = findViewById<TextView>(R.id.modalMessage)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        modalMessage.text = "¿Estás seguro de continuar?"

        btnConfirm.setOnClickListener {
            // Lógica para confirmar
            onConfirm.invoke()
            dismiss()
        }

        btnCancel.setOnClickListener {
            // Lógica para cancelar
            dismiss()
        }
    }
}