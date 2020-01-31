package com.mengxyz.giftshopkolinedition.db.model

import com.google.firebase.Timestamp

data class ProductModel2(
    override var u_id: String? = null,
//    override var u_name: String?,
//    override var u_pic: String?,
    override var name: String? = null,
    override var description: String? = null,
    override var tel: String? = null,
    override var picture: MutableList<String>? = null,
    override var picture_url: MutableList<String>? = null,
    override var product_id: String? = null,
    override var price: Double? = null,
    override var facebook_name: String? = null,
    override var facebook_url: String? = null,
    override var line_url: String? = null,
    override var line_id: String? = null,
    override var lat: String? = null,
    override var lon: String? = null,
    override var timestamps: Any? = null
):ProductModelImpl