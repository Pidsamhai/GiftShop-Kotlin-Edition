package com.mengxyz.giftshopkolinedition.ui.activity.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.ui.activity.home.HomeActivity
import com.mengxyz.giftshopkolinedition.ui.activity.register.RegisterActivity
import com.mengxyz.giftshopkolinedition.ui.scope.ActivityScope
import com.mengxyz.giftshopkolinedition.utils.Validator
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : ActivityScope(),
    View.OnClickListener,
    OnFailureListener,
    OnCompleteListener<AuthResult> {

    private val mAuth by inject<FirebaseAuth>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.statusBarColor = Color.parseColor("#FF121212")
        b_login.setOnClickListener(this)
        t_register.setOnClickListener(this)
        e_email.addTextChangedListener(Validator.Email(e_email, l_email))
        e_password.addTextChangedListener(Validator.Password(e_password, l_password))
    }

    private fun emailLogin() {
        if (l_email.isErrorEnabled
            || l_password.isErrorEnabled
            || e_email.text.toString().isEmpty()
            || e_password.text.toString().isEmpty()) {

            showSnackBar("Please enter Email or Password")
            return
        }
        showLoading()
        mAuth.signInWithEmailAndPassword(
            e_email.text.toString(),
            e_password.text.toString()
        )
            .addOnCompleteListener(this)
            .addOnFailureListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            b_login -> {
                emailLogin()
            }
            t_register -> {
                startActivity(Intent(this,RegisterActivity::class.java))
            }
        }
    }

    override fun onFailure(e: Exception) {
        showToast(e.toString())
        hideLoading()
    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            showToast(mAuth.currentUser?.email!!)
            hideLoading()
            startActivity(Intent(this,HomeActivity::class.java))
            finishAffinity()
        }
    }
}
