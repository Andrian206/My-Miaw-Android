package com.pab.mymiaw

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private var isCheckingAuth = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        splashScreen.setKeepOnScreenCondition { isCheckingAuth }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup NavigationView with NavController
        NavigationUI.setupWithNavController(navView, navController)

        // Handle navigation item clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.aboutFragment -> {
                    navController.navigate(R.id.aboutFragment)
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }

        // Setup Sign Out button
        navView.findViewById<android.widget.Button>(R.id.btn_sign_out)?.setOnClickListener {
            auth.signOut()
            drawerLayout.closeDrawers()
            navController.navigate(R.id.signInFragment)
        }

        ViewCompat.setOnApplyWindowInsetsListener(drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkAuthAndNavigate()
    }

    private fun checkAuthAndNavigate() {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            isCheckingAuth = false
        } else {
            isCheckingAuth = false
        }
    }
}