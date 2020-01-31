package com.mengxyz.giftshopkolinedition.ui.fragment.addproduct

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import coil.api.load
import com.mengxyz.giftshopkolinedition.R

class PagerAdapter(val context: Context,val imgs : MutableList<Uri>) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj as RelativeLayout

    override fun getCount(): Int = imgs.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.viewpager_item,container,false)
        v.findViewById<ImageView>(R.id.lbg).load(imgs[position])
        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as RelativeLayout)
    }

    fun getAllItem(): MutableList<Uri> {
        return imgs
    }

    fun addItem(img:Uri){
        imgs.add(img)
    }

    fun removeItem(index:Int){
        imgs.removeAt(index)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun getItemSize():Int{
        return imgs.size
    }
}