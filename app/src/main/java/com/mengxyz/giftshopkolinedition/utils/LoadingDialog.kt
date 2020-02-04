package com.mengxyz.giftshopkolinedition.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mengxyz.giftshopkolinedition.R

class LoadingDialog(private val context: Context) {
    @SuppressLint("SetTextI18n")
    fun create(): AlertDialog {
        val v = (context as AppCompatActivity).layoutInflater.inflate(R.layout.loading_dialog,null)
        val adg = AlertDialog.Builder(context)
            .setView(v)
            .setNegativeButton(R.string.cancel){_,_->
                context.finish()
            }
            .setCancelable(false)
            .create()
        adg.setOnShowListener {
            adg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE)
        }
        return adg
    }
}