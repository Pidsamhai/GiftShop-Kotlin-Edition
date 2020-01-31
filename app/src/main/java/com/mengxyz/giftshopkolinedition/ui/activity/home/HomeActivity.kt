package com.mengxyz.giftshopkolinedition.ui.activity.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.mengxyz.giftshopkolinedition.R
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val mAuth by inject<FirebaseAuth>()

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
        val user = mAuth.currentUser
        val rnd = Random.nextInt(0,100).toString()
        val header = navigationView.getHeaderView(0)
        if(user != null){
            header.apply {
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