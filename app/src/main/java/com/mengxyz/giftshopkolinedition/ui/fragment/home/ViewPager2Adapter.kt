package com.mengxyz.giftshopkolinedition.ui.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import com.mengxyz.giftshopkolinedition.R
import kotlinx.android.synthetic.main.viewpager_item.view.*


class ViewPager2Adapter(private val items: MutableList<String>?, private val context: Context) :
    RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>() {

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.viewpager_item,parent,false)
        val lp:RecyclerView.LayoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        view.layoutParams = lp
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.lbg.load(items?.get(position)){
            scale(Scale.FILL)
        }
    }
}
