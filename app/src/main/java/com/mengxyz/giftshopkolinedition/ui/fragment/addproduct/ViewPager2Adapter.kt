package com.mengxyz.giftshopkolinedition.ui.fragment.addproduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.mengxyz.giftshopkolinedition.R

private val colors = intArrayOf(
    android.R.color.black,
    android.R.color.holo_red_light,
    android.R.color.holo_blue_dark,
    android.R.color.holo_purple
)

class ViewPager2Adapter() : RecyclerView.Adapter<ViewHolderExample>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderExample =
        ViewHolderExample(
            LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item,parent,false)
        )

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(
        holder: ViewHolderExample,
        position: Int
    ) {
        with(holder.itemView) {
            findViewById<LinearLayout>(R.id.lbg).setBackgroundResource(colors[position])
        }
    }

}
class ViewHolderExample(
    itemView: View
) : RecyclerView.ViewHolder(itemView)
