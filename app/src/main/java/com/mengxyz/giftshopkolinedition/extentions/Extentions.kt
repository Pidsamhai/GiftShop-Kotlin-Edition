package com.mengxyz.giftshopkolinedition.extentions

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.mengxyz.giftshopkolinedition.db.adapter.ProductRecycleItems
import com.mengxyz.giftshopkolinedition.db.model.ProductModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun List<ProductModel>.toFutureWeatherItems() : List<ProductRecycleItems> {
    return this.map {
        ProductRecycleItems(it)
    }
}

fun FirebaseUser?.isNotnull(): Boolean {
    return FirebaseAuth.getInstance().currentUser != null
}

suspend fun <TResult> Task<TResult>.await():TResult  {
    if (isComplete) {
        val e = exception as FirebaseAuthException?
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException("Task $this was cancelled normally.")
            } else {
                @Suppress("UNCHECKED_CAST")
                result as TResult
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                @Suppress("UNCHECKED_CAST")
                if (isCanceled) cont.cancel() else cont.resume(result as TResult)
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}
