package com.mengxyz.giftshopkolinedition.ui.fragment.addproduct

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.mengxyz.giftshopkolinedition.db.model.ProductModel2
import com.mengxyz.giftshopkolinedition.db.repo.FireStoreRepository
import com.mengxyz.giftshopkolinedition.extentions.await
import java.util.*

class AddProductFragmentViewModel(
    private val fireStoreRepository: FireStoreRepository
):ViewModel() {
    private var productData:MutableLiveData<ProductModel2> = MutableLiveData()

    suspend fun addProduct(product:ProductModel2):Any{
        val uuid = UUID.randomUUID().toString()
        product.product_id = uuid
        return try {
            val res = fireStoreRepository.addProduct()
                .document(uuid)
                .set(product)
                .await()
            Log.e("FromViewModel", "Returned")
            0
        }catch (e:Exception){
            Log.e("FromViewModel", "Returned")
            e
        }
    }

    suspend fun uploadImg(uri: Uri):Any{
        val uuid = UUID.randomUUID().toString()
        val img_name = "$uuid.png"
        val productRef = fireStoreRepository.uploadImg().child(img_name)
        return  try {
            productRef.putFile(uri).await()
            val url  = productRef.downloadUrl.await()
            Pair(img_name,url.toString())
        }catch (e:Exception){
            e
        }
    }

    fun getProduct(docID:String):LiveData<ProductModel2>{
        fireStoreRepository.getProduct().document(docID).addSnapshotListener(EventListener<DocumentSnapshot>{v,e->
            if(e!=null){
                productData.value = null
                return@EventListener
            }
            productData.value = v?.toObject(ProductModel2::class.java)
        })

        return productData
    }
}