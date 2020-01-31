package com.mengxyz.giftshopkolinedition.db.adapter

import android.content.Intent
import android.view.View
import coil.api.load
import coil.size.Scale
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.db.model.ProductModel
import com.mengxyz.giftshopkolinedition.ui.activity.product_info.ProductInfoActivity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.product_item.*


class ProductRecycleItems(product: ProductModel) : Item() {
    val mProduct = product
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            title.text = mProduct.name
            image.load(mProduct.imgUrl) {
                scale(Scale.FIT)
                error(R.drawable.ic_error)
            }

            image.setOnClickListener {
                val intent = Intent(containerView.context,ProductInfoActivity::class.java)
                intent.putExtra("imgBitmap",mProduct.imgUrl)
                containerView.context.startActivity(intent)
            }

            btn_expand.setOnClickListener {
                if (more_content.visibility == View.GONE) {
                    more_content.visibility = View.VISIBLE
                } else
                    more_content.visibility = View.GONE
            }

        }
    }

    override fun getLayout(): Int = R.layout.product_item
}