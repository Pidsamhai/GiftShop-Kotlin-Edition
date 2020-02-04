package com.mengxyz.giftshopkolinedition.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.mengxyz.giftshopkolinedition.db.repo.FireStoreRepository
import com.mengxyz.giftshopkolinedition.ui.activity.product_info.ProductInfoViewModel
import com.mengxyz.giftshopkolinedition.ui.fragment.addproduct.AddProductFragmentViewModel
import com.mengxyz.giftshopkolinedition.ui.fragment.home.HomeFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val STORAGE_URL = "gs://gift-shop-kotlin.appspot.com"
private const val MAX_RETIRE:Long = 0

val myModule = module {
    viewModel { AddProductFragmentViewModel(get()) }
    viewModel { HomeFragmentViewModel(get()) }
    viewModel { ProductInfoViewModel(get()) }
    single { FireStoreRepository(get(),get()) }
    single {
        val instance = FirebaseFirestore.getInstance()
        instance.firestoreSettings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
        instance
    }
    single {
        val instance = FirebaseStorage.getInstance(STORAGE_URL).apply {
            maxUploadRetryTimeMillis = MAX_RETIRE
            maxDownloadRetryTimeMillis = MAX_RETIRE
            maxOperationRetryTimeMillis = MAX_RETIRE
        }
        instance
    }
    single {FirebaseAuth.getInstance()}
}