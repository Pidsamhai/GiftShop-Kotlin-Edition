package com.mengxyz.giftshopkolinedition.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionManager(private val context: Context,private val activity: Activity) {
    private val LOCATION = 19
    fun LOCATION_STATUS() = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun askLocationPermission(){
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION
        )
    }
}