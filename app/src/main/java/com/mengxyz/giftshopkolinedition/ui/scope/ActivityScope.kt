package com.mengxyz.giftshopkolinedition.ui.scope

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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
        initLoading()
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showLoading() = adg.show()
    fun hideLoading() = adg.dismiss()

    fun showToast(msg: String,duration:Int = Toast.LENGTH_SHORT) = Toast.makeText(this,msg,duration).show()

    fun showSnackBar(msg: String,duration: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(window.decorView.rootView,msg,duration).show()

    private fun initLoading(){
        adg = LoadingDialog(this).create()
    }

    fun setLoadingCallback(callback:DialogInterface.OnClickListener?){
        adg = LoadingDialog(this,callback).create()
    }
}