package com.pab.mymiaw

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager
    private var isPasswordVisible = false

    companion object {
        private const val TAG = "SignInFragment"
        // Web Client ID from google-services.json (oauth_client with client_type: 3)
        private const val WEB_CLIENT_ID = "731447226829-urgrd1fqhkc0bkgvpjv9sf7qpbkub1pq.apps.googleusercontent.com"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        credentialManager = CredentialManager.create(requireContext())

        val etEmail = view.findViewById<EditText>(R.id.et_email_sign_in)
        val etPassword = view.findViewById<EditText>(R.id.et_password_sign_in)
        val btnSignIn = view.findViewById<Button>(R.id.btn_sign_in)
        val btnGoogleSignIn = view.findViewById<Button>(R.id.btn_google_sign_in)
        val tvToSignUp = view.findViewById<TextView>(R.id.tv_sign_up)
        val ivTogglePassword = view.findViewById<ImageView>(R.id.iv_toggle_password)

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

        // Email/Password Sign In
        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty()) {
                etEmail.error = "Email wajib diisi"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Password wajib diisi"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, "Login Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Google Sign In
        btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }

        // Navigate to Sign Up
        tvToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun signInWithGoogle() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(WEB_CLIENT_ID)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = requireContext()
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Google Sign In failed", e)
                Toast.makeText(context, "Google Sign In gagal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                        Toast.makeText(context, "Token tidak valid", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                }
            }
            else -> {
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    
                    // Save user info to Firestore if new user
                    user?.let { firebaseUser ->
                        val db = FirebaseFirestore.getInstance()
                        val userRef = db.collection("users").document(firebaseUser.uid)
                        
                        userRef.get().addOnSuccessListener { document ->
                            if (!document.exists()) {
                                // New user, save to Firestore
                                val userData = hashMapOf(
                                    "username" to (firebaseUser.displayName ?: "User"),
                                    "email" to firebaseUser.email
                                )
                                userRef.set(userData)
                            }
                            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                        }.addOnFailureListener {
                            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication gagal.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}