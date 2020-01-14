package com.mengxyz.giftshopkolinedition.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.mengxyz.giftshopkolinedition.db.model.ProductModel
import com.mengxyz.giftshopkolinedition.db.model.UserModel
import com.mengxyz.giftshopkolinedition.db.repo.FireStoreRepository

const val TAG = "FIRE_STORE_VIEW_MODEL"

class HomeFragmentViewModel : ViewModel(){
    private var fireStoreRepository = FireStoreRepository()
    private var userData : MutableLiveData<UserModel> = MutableLiveData()
    private var productData : MutableLiveData<List<ProductModel>> = MutableLiveData()

    fun getUserdata():LiveData<UserModel>{
        fireStoreRepository.getUserdata().document("user1").addSnapshotListener{value,e->
            if(e!=null) {
                userData.value = null
            }
            userData.value = value?.toObject(UserModel::class.java)
        }
        return userData
    }

    fun getAllProduct():LiveData<List<ProductModel>>{
        fireStoreRepository.getAllProduct().addSnapshotListener(EventListener<QuerySnapshot>{value,e->
            if(e!=null){
                productData.value = null
                return@EventListener
            }
            productData.value = value?.toObjects(ProductModel::class.java)
        })

        return productData
    }

    fun refreshAllProduct(){
        getAllProduct()
    }

}