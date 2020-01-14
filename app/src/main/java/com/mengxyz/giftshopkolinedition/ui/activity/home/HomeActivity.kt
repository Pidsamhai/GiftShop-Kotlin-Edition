package com.mengxyz.giftshopkolinedition.ui.activity.home

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import coil.api.load
import com.google.firebase.auth.FirebaseAuth
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.ui.fragment.home.HomeFragment
import com.mengxyz.giftshopkolinedition.ui.fragment.myproduct.MyProductFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.statusBarColor = myAppbar.solidColor
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setSupportActionBar(myAppbar as Toolbar)
        fab.setOnClickListener {
            navController.navigate(R.id.addProductFragment)
        }

        initUserInfo()
        initFragment()
    }

    override fun onResume() {
        super.onResume()
        initUserInfo()
    }

    private fun initUserInfo(){
        val user = FirebaseAuth.getInstance().currentUser
        val rnd = Random.nextInt(0,100).toString()
        val header = navigationView.getHeaderView(0)
        if(user != null){
            header.apply {
                //findViewById<ImageView>(R.id.navigation_header_cover).load("https://raw.githubusercontent.com/vincentmorneau/material-apex/master/docs/img/home.jpg")
                findViewById<TextView>(R.id.navigation_header_name).text = user.email
            }
        }else{
            header.apply {
                findViewById<TextView>(R.id.navigation_header_name).text = rnd
            }
        }
    }

    private fun initFragment(){
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        navigationView.setupWithNavController(navController)

        myAppbar.setNavigationOnClickListener {
            drawer_layout.openDrawer(navigationView)
        }

        navigationView.setNavigationItemSelectedListener {
            if(it.itemId != navController.currentDestination?.id){
                navController.navigate(it.itemId)
            }
            drawer_layout.closeDrawer(navigationView)
            return@setNavigationItemSelectedListener true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.homeFragment ||destination.id == R.id.myProductFragment)
                fab.show()
            else{
                fab.hide()
            }
        }
    }
}