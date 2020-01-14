package com.mengxyz.giftshopkolinedition.db.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

const val COLLECTION = "user"
const val PRODUCT_COLLECTION = "product"

class FireStoreRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    }

    fun getUserdata():CollectionReference{
        return fireStoreDB.collection(COLLECTION)
    }

    fun getAllProduct():CollectionReference{
        return fireStoreDB.collection(PRODUCT_COLLECTION)
    }
}