package com.mengxyz.giftshopkolinedition.db.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

const val COLLECTION = "user"
const val PRODUCT_COLLECTION = "product"
const val PRODUCT_COLLECTION2 = "product2"
const val STORAGE_IMG_PATH = "product2"

class FireStoreRepository(
    private val fireStoreDB:FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) {
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