package com.mengxyz.giftshopkolinedition.db.model

import com.google.firebase.Timestamp

interface ProductModelImpl {
    var u_id: String?
//    var u_name: String?
//    var u_pic: String?

    var name: String?
    var description: String?
    var tel: String?
    var picture: MutableList<String>?
    var picture_url: MutableList<String>?
    var product_id: String?
    var price: Double?
    var facebook_name: String?
    var facebook_url: String?
    var line_url: String?
    var line_id: String?
    var lat: String?
    var lon: String?
    var timestamps: Any?
}