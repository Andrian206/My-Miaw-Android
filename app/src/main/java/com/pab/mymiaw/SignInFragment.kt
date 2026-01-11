package com.pab.mymiaw

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val etUsername = view.findViewById<EditText>(R.id.et_username)
        val etPassword = view.findViewById<EditText>(R.id.et_password)
        val ivTogglePassword = view.findViewById<ImageView>(R.id.iv_toggle_password)
        val btnSignIn = view.findViewById<Button>(R.id.btn_sign_in)
        val btnGoogleSignIn = view.findViewById<Button>(R.id.btn_google_sign_in)
        val tvSignUp = view.findViewById<TextView>(R.id.tv_sign_up)

        // Toggle password visibility
        ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.ic_visibility)
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.ic_visibility_off)
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // Sign In button click
        btnSignIn.setOnClickListener {
            val email = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etUsername.error = "Email is required"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Password is required"
                return@setOnClickListener
            }

            signInWithEmail(email, password)
        }

        // Google Sign In button click
        btnGoogleSignIn.setOnClickListener {
            Toast.makeText(context, "Google Sign In coming soon", Toast.LENGTH_SHORT).show()
        }

        // Navigate to Sign Up
        tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                } else {
                    Toast.makeText(
                        context,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}