package develpment.myapplication.model

import android.view.View

interface OnItemClickListenerTemplate<T> {
    fun onItemClick(view: View, position: Int, dato: T)
}


