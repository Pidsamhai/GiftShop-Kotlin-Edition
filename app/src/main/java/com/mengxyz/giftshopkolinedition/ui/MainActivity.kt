package com.mengxyz.giftshopkolinedition.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mengxyz.giftshopkolinedition.BuildConfig
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.ui.activity.home.HomeActivity
import com.mengxyz.giftshopkolinedition.ui.activity.login.LoginActivity
import com.rbddevs.splashy.Splashy
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val mAuth by inject<FirebaseAuth>()
    private val user = mAuth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!BuildConfig.DEBUG){
            Splashy(this)
                .setTitle("GiftShop Kotlin Edition")
                .setSubTitle(Build.getRadioVersion())
                .setFullScreen(true)
                .showProgress(true)
                .setTime(5000)
                .setBackgroundColor(R.color.colorPrimary)
                .setSubTitleColor(R.color.colorPrimaryDark)
                .showLogo(false)
                .setTitleSize(25.0f)
                .setSubTitleSize(15.0f)
                .show()
            Splashy.onComplete(object: Splashy.OnComplete{
                override fun onComplete() {
                    startActivity(Intent(this@MainActivity.applicationContext,HomeActivity::class.java))
                    finish()
                }
            })
        }else{
            if(user!=null){
                startActivity(Intent(this@MainActivity.applicationContext,HomeActivity::class.java))
            }else{
                startActivity(Intent(this@MainActivity.applicationContext,LoginActivity::class.java))
            }
            finish()
        }
    }
}
