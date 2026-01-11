package com.pab.mymiaw

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val etEmail = view.findViewById<EditText>(R.id.et_email_sign_in)
        val etPassword = view.findViewById<EditText>(R.id.et_password_sign_in)
        val btnSignIn = view.findViewById<Button>(R.id.btn_sign_in)
        val tvToSignUp = view.findViewById<TextView>(R.id.tv_sign_up)

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                        } else {
                            Toast.makeText(context, "Login Gagal!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // Teks "Belum punya akun? Daftar" pindah ke SignUpFragment
        tvToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}