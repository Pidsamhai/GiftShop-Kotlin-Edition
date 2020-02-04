package com.mengxyz.giftshopkolinedition.ui.scope

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mengxyz.giftshopkolinedition.utils.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class ActivityScope: AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    private lateinit var adg:AlertDialog
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        adg = LoadingDialog(context = this).create()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showLoading(){
        adg.show()
    }
    fun hideLoading(){
        adg.dismiss()
    }
}