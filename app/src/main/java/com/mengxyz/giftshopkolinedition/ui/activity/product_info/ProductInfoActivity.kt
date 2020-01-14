package com.mengxyz.giftshopkolinedition.ui.activity.product_info

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.size.Scale
import com.mengxyz.giftshopkolinedition.R
import kotlinx.android.synthetic.main.activity_product_info.*


class ProductInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
        val window: Window = window
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        val imgUrl = intent.getStringExtra("imgBitmap")

        image.load(imgUrl){
            crossfade(true)
            scale(Scale.FIT)
        }

        fab_back.setOnClickListener {
            finish()
        }

    }
}