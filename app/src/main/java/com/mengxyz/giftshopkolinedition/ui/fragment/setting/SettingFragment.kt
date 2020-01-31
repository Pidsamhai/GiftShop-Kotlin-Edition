package com.mengxyz.giftshopkolinedition.ui.fragment.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.ui.activity.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment(),View.OnClickListener,FirebaseAuth.AuthStateListener {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.appbar_title).text = " Setting "
        b_logout.setOnClickListener(this)
        mAuth.addAuthStateListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            b_logout ->{
                mAuth.signOut()
            }
        }
    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        if(firebaseAuth.currentUser == null){
            startActivity(Intent(this@SettingFragment.context,LoginActivity::class.java))
            (activity as FragmentActivity).finishAffinity()
        }
    }
}