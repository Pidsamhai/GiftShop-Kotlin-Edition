package com.mengxyz.giftshopkolinedition.ui.activity.product_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.mengxyz.giftshopkolinedition.db.model.ProductModel
import com.mengxyz.giftshopkolinedition.db.repo.FireStoreRepository

class ProductInfoViewModel(
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {
    private val productData: MutableLiveData<ProductModel> = MutableLiveData()
    fun getItem(documentID: String): LiveData<ProductModel> {
        fireStoreRepository.getProduct().document(documentID)
            .addSnapshotListener(EventListener<DocumentSnapshot> { v, e ->
                if (e != null) {
                    productData.value = null
                    return@EventListener
                }
                productData.value = v?.toObject(ProductModel::class.java)
            })

        return productData
    }
}