package com.mengxyz.giftshopkolinedition.ui.fragment.addproduct

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mengxyz.giftshopkolinedition.db.model.ProductModel
import com.mengxyz.giftshopkolinedition.db.repo.FireStoreRepository
import com.mengxyz.giftshopkolinedition.extentions.await
import timber.log.Timber
import java.util.*

class AddProductFragmentViewModel(
    private val fireStoreRepository: FireStoreRepository
):ViewModel() {
    private var productData:MutableLiveData<ProductModel> = MutableLiveData()

    suspend fun addProduct(product:ProductModel):Any{
        val uuid = UUID.randomUUID().toString()
        product.product_id = uuid
        return try {
            val res = fireStoreRepository.addProduct()
                .document(uuid)
                .set(product)
                .await()
            Timber.e("Returned")
            0
        }catch (e:Exception) {
            Timber.e("Returned")
            e
        }
    }

    suspend fun uploadImg(uri: Uri):Any{
        val uuid = UUID.randomUUID().toString()
        val imgName = "$uuid.png"
        val productRef = fireStoreRepository.uploadImg().child(imgName)
        return  try {
            productRef.putFile(uri).await()
            val url  = productRef.downloadUrl.await()
            Pair(imgName,url.toString())
        }catch (e:Exception){
            e
        }
    }
}