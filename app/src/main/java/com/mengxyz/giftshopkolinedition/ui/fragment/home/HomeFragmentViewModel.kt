package com.mengxyz.giftshopkolinedition.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.mengxyz.giftshopkolinedition.db.model.ProductModel2
import com.mengxyz.giftshopkolinedition.db.model.UserModel
import com.mengxyz.giftshopkolinedition.db.repo.FireStoreRepository

const val TAG = "FIRE_STORE_VIEW_MODEL"

class HomeFragmentViewModel(
    private var fireStoreRepository:FireStoreRepository
) : ViewModel(){
    private var userData : MutableLiveData<UserModel> = MutableLiveData()
    private var productData : MutableLiveData<List<ProductModel2>> = MutableLiveData()

    fun getUserdata():LiveData<UserModel>{
        fireStoreRepository.getUserdata().document("user1").addSnapshotListener{value,e->
            if(e!=null) {
                userData.value = null
            }
            userData.value = value?.toObject(UserModel::class.java)
        }
        return userData
    }

    fun getAllProduct():LiveData<List<ProductModel2>>{
        fireStoreRepository.getAllProduct().addSnapshotListener(EventListener<QuerySnapshot>{value,e->
            if(e!=null){
                productData.value = null
                return@EventListener
            }
            productData.value = value?.toObjects(ProductModel2::class.java)
        })

        return productData
    }

    fun refreshAllProduct(){
        getAllProduct()
    }

}