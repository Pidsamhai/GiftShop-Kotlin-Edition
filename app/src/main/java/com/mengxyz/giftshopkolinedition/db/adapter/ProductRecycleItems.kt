package com.mengxyz.giftshopkolinedition.db.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.transition.AutoTransition
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.api.load
import coil.size.Scale
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.db.model.ProductModel2
import com.mengxyz.giftshopkolinedition.ui.activity.product_info.ProductInfoActivity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.product_item.*


class ProductRecycleItems(private val product: ProductModel2) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            title.text = this@ProductRecycleItems.product.name
            image.load(this@ProductRecycleItems.product.picture_url?.get(0)) {
                scale(Scale.FIT)
                error(R.drawable.ic_error)
            }

            image.setOnClickListener {
                val intent = Intent(containerView.context,ProductInfoActivity::class.java)
                intent.putExtra("documentID", this@ProductRecycleItems.product.product_id)
                containerView.context.startActivity(intent)
            }

            btn_expand.setOnClickListener {
                if (more_content.visibility == View.GONE) {
                    TransitionManager.beginDelayedTransition(
                        containerView as ViewGroup, AutoTransition()
                    )
                    more_content.visibility = View.VISIBLE
                } else{
                    TransitionManager.beginDelayedTransition(
                        more_content as ViewGroup,ChangeBounds()
                    )
                    more_content.visibility = View.GONE
                }


            }

        }
    }

    override fun getLayout(): Int = R.layout.product_item
}