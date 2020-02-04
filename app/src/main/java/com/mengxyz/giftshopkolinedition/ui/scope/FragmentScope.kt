package com.mengxyz.giftshopkolinedition.ui.scope

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mengxyz.giftshopkolinedition.utils.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class FragmentScope : Fragment(), CoroutineScope {
    private lateinit var job: Job
    private lateinit var adg: AlertDialog
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLoading()
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("onDestroy: Called ")
        job.cancel()
    }

    fun showSnackBar(msg: String,duration: Int = Snackbar.LENGTH_SHORT) = view?.rootView?.let { Snackbar.make(it, msg, duration).show() }


    fun showLoading() = adg.show()
    fun hideLoading() = adg.dismiss()

    fun showToast(msg: String,duration:Int = Toast.LENGTH_SHORT) = Toast.makeText(this.context,msg,duration).show()

    private fun initLoading(){
        adg = LoadingDialog(this.context!!).create()
    }

    fun setLoadingCallback(callback:DialogInterface.OnClickListener){
        adg = LoadingDialog(this.context!!,callback).create()
    }

}