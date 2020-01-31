package com.mengxyz.giftshopkolinedition.ui.scope

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class FragmentScope :Fragment(),CoroutineScope{
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("fragment Scope", "onDestroy: Called ")
        job.cancel()
    }

    fun showSnackBar(msg:String){
        view?.rootView?.let { Snackbar.make(it,msg,Snackbar.LENGTH_SHORT).show() }
    }

}