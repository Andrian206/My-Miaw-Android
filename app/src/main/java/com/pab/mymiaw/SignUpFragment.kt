package com.pab.mymiaw

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val etUsername = view.findViewById<EditText>(R.id.et_username_sign_up)
        val etEmail = view.findViewById<EditText>(R.id.et_email_sign_up)
        val etPassword = view.findViewById<EditText>(R.id.et_password_sign_up)
        val etConfirmPassword = view.findViewById<EditText>(R.id.et_confirm_password_sign_up)
        val btnSignUp = view.findViewById<Button>(R.id.btn_sign_up)
        val tvToSignIn = view.findViewById<TextView>(R.id.tv_sign_in)
        val ivTogglePassword = view.findViewById<ImageView>(R.id.iv_toggle_password)
        val ivToggleConfirmPassword = view.findViewById<ImageView>(R.id.iv_toggle_confirm_password)

        // Toggle password visibility
        ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.transformationMethod = null
                ivTogglePassword.setImageResource(R.drawable.ic_visibility)
            } else {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                ivTogglePassword.setImageResource(R.drawable.ic_visibility_off)
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // Toggle confirm password visibility
        ivToggleConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                etConfirmPassword.transformationMethod = null
                ivToggleConfirmPassword.setImageResource(R.drawable.ic_visibility)
            } else {
                etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                ivToggleConfirmPassword.setImageResource(R.drawable.ic_visibility_off)
            }
            etConfirmPassword.setSelection(etConfirmPassword.text.length)
        }

        btnSignUp.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Validation
            if (username.isEmpty()) {
                etUsername.error = "Username wajib diisi"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                etEmail.error = "Email wajib diisi"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Password wajib diisi"
                return@setOnClickListener
            }
            if (password.length < 6) {
                etPassword.error = "Password minimal 6 karakter"
                return@setOnClickListener
            }
            if (confirmPassword.isEmpty()) {
                etConfirmPassword.error = "Konfirmasi password wajib diisi"
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                etConfirmPassword.error = "Password tidak cocok"
                return@setOnClickListener
            }

            // Create user with Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Save user data to Firestore
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            val userData = hashMapOf(
                                "username" to username,
                                "email" to email
                            )
                            db.collection("users").document(userId).set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Daftar Berhasil!", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Daftar Berhasil!", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                                }
                        }
                    } else {
                        Toast.makeText(context, "Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // Navigate to Sign In
        tvToSignIn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}