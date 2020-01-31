package com.mengxyz.giftshopkolinedition.db.repo

import android.net.Uri
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mengxyz.giftshopkolinedition.db.model.ProductModel2

const val COLLECTION = "user"
const val PRODUCT_COLLECTION = "product"
const val PRODUCT_COLLECTION2 = "product2"
const val STORAGE_URL = "gs://gift-shop-kotlin.appspot.com"
const val STORAGE_IMG_PATH = "product2"
const val MAX_RETIRE:Long = 0

class FireStoreRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    }
    private val firebaseStorage = FirebaseStorage.getInstance(STORAGE_URL).apply {
        maxUploadRetryTimeMillis = MAX_RETIRE
        maxDownloadRetryTimeMillis = MAX_RETIRE
        maxOperationRetryTimeMillis = MAX_RETIRE
    }

    fun getUserdata():CollectionReference{
        return fireStoreDB.collection(COLLECTION)
    }

    fun getAllProduct():CollectionReference{
        return fireStoreDB.collection(PRODUCT_COLLECTION)
    }

    fun getProduct():CollectionReference{
        return fireStoreDB.collection(PRODUCT_COLLECTION2)
    }

    fun addProduct():CollectionReference{
        return fireStoreDB.collection(PRODUCT_COLLECTION2)
    }

    fun uploadImg(): StorageReference {
        return firebaseStorage.reference.child(STORAGE_IMG_PATH)
    }
}