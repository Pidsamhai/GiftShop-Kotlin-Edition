package com.mengxyz.giftshopkolinedition.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.extentions.await
import com.mengxyz.giftshopkolinedition.extentions.isNotnull
import com.mengxyz.giftshopkolinedition.ui.activity.home.HomeActivity
import com.mengxyz.giftshopkolinedition.ui.scope.ActivityScope
import com.mengxyz.giftshopkolinedition.utils.Validator
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RegisterActivity : ActivityScope(), View.OnClickListener {

    private val mAuth by inject<FirebaseAuth>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        e_email.addTextChangedListener(Validator.Email(e_email, l_email))
        e_password.addTextChangedListener(
            Validator.Password(
                e_password,
                l_password,
                e_cpassword,
                l_cpassword
            )
        )
        e_cpassword.addTextChangedListener(
            Validator.ConfirmPassword(
                e_password,
                e_cpassword,
                l_cpassword
            )
        )
        b_register.setOnClickListener(this)
    }

    private fun createAccount() = launch(Dispatchers.Main) {
        showLoading()
        try {
             mAuth.createUserWithEmailAndPassword(e_email.text.toString(), e_password.text.toString()).await()
            showToast(mAuth.currentUser?.email!!)
        }catch (e:FirebaseAuthException){
            showToast(e.message.toString())
        }
        hideLoading()
        if(mAuth.currentUser.isNotnull()){
            launchHomeActivity()
        }
    }


    private fun launchHomeActivity(){
        startActivity(Intent(this,HomeActivity::class.java))
        finishAffinity()
    }



    private fun inputIsError(): Boolean {
        return (e_email.text.toString().isEmpty() ||
                l_email.isErrorEnabled ||
                e_password.text.toString().isEmpty() ||
                l_password.isErrorEnabled ||
                e_cpassword.text.toString().isEmpty() ||
                l_cpassword.isErrorEnabled)
    }

    override fun onClick(v: View?) {
        when (v) {
            b_register -> {
                if (inputIsError()) {
                    showToast("Some thing wrong!!")
                    return
                }
                createAccount()
            }
        }
    }
}


