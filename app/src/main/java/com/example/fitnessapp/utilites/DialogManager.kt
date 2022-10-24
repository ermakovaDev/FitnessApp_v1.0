package com.example.fitnessapp.utilites

import android.app.AlertDialog
import android.content.Context
import com.example.fitnessapp.R


object DialogManager {  // if specify is OBJECT -> no init needed. if specify is CLASS -> need a instance

    fun showDialog(context: Context, messageId: Int, listener: Listener){
        val builder = AlertDialog.Builder(context)
        var dialog: AlertDialog? = null
        builder.setTitle(R.string.warning)
        builder.setMessage(messageId)
        builder.setPositiveButton(R.string.reset){ _,_ ->
            run {
                listener.onClick()
                dialog?.dismiss()
            }
        }

        builder.setNegativeButton(R.string.cancel){ _,_ ->
            run {
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.show()
    }

    interface Listener{
        fun onClick()
    }
}