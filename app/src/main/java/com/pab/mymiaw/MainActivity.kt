package com.pab.mymiaw

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var isCheckingAuth = true

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen sebelum super.onCreate()
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Inisialisasi Firebase terlebih dahulu
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        // Set kondisi splash screen untuk tetap tampil selama pengecekan auth
        splashScreen.setKeepOnScreenCondition { isCheckingAuth }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cek auth setelah UI siap
        checkAuthAndNavigate()
    }

    private fun checkAuthAndNavigate() {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            // User belum login
            isCheckingAuth = false
        } else {
            // User sudah login, tampilkan HomeFragment
            isCheckingAuth = false
        }
    }
}