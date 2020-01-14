package com.mengxyz.giftshopkolinedition.ui.activity.login

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.ui.activity.home.HomeActivity
import com.mengxyz.giftshopkolinedition.ui.activity.register.RegisterActivity
import com.mengxyz.giftshopkolinedition.utils.Validator
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.e_email
import kotlinx.android.synthetic.main.activity_login.e_password
import kotlinx.android.synthetic.main.activity_login.l_email
import kotlinx.android.synthetic.main.activity_login.l_password

class LoginActivity : AppCompatActivity(),
    View.OnClickListener,
    OnFailureListener,
    OnCompleteListener<AuthResult> {

    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var materialAlertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.statusBarColor = Color.parseColor("#FF121212")
        b_login.setOnClickListener(this)
        t_register.setOnClickListener(this)
        e_email.addTextChangedListener(Validator.Email(e_email, l_email))
        e_password.addTextChangedListener(Validator.Password(e_password, l_password))
        materialAlertDialog = MaterialAlertDialogBuilder(this)
            .setTitle("Login")
            .setMessage("loading...")
            .setCancelable(false)
            .setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
                    materialAlertDialog.dismiss()
                })
            .create()
    }

    private fun emailLogin() {
        if (l_email.isErrorEnabled
            || l_password.isErrorEnabled
            || e_email.text.toString().isEmpty()
            || e_password.text.toString().isEmpty()) {
            Snackbar.make(window.decorView.rootView,"Please enter Email or Password",Snackbar.LENGTH_SHORT).show()
            return
        }
        materialAlertDialog.show()
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
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        materialAlertDialog.dismiss()
    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Toast.makeText(this, mAuth.currentUser?.email, Toast.LENGTH_SHORT).show()
            materialAlertDialog.dismiss()
            startActivity(Intent(this,HomeActivity::class.java))
            finishAffinity()
        }
    }
}
