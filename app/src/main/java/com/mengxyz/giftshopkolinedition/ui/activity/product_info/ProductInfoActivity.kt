package com.mengxyz.giftshopkolinedition.ui.activity.product_info

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.ui.fragment.home.ViewPager2Adapter
import com.mengxyz.giftshopkolinedition.ui.scope.ActivityScope
import com.mengxyz.giftshopkolinedition.utils.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.activity_product_info.*
import org.koin.android.ext.android.inject


class ProductInfoActivity : ActivityScope() {

    private val viewModel by inject<ProductInfoViewModel>()

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
        showLoading()
        val documentID = intent.getStringExtra("documentID")
        viewModel.getItem(documentID = documentID).observe(this, Observer {
            if (it == null)
                return@Observer
            Log.e("ViewModel", it.toString())
            hideLoading()
            it.picture_url?.let { it1 -> initViewpager(it1) }
        })


        fab_back.setOnClickListener {
            finish()
        }

    }

    private fun initViewpager(imgs: MutableList<String>) {
        viewpager2.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = ViewPager2Adapter(imgs, context = this@ProductInfoActivity)
            setPageTransformer(ZoomOutPageTransformer())
        }
    }
}