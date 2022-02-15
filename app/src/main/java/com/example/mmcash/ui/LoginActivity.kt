package com.example.mmcash.ui


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mmcash.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Login"

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.apply {
            signupText.setOnClickListener {
                startActivity(
                    Intent(
                        applicationContext,
                        SignUpActivity::class.java
                    )
                )
                finish()
            }

            btnLogin.setOnClickListener {
                validateData()
            }
        }
    }

    private fun validateData() {
        email = binding.emailLogin.text.toString().trim()
        password = binding.passwordLogin.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            binding.emailLogin.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordLogin.error = "Please enter password"
        } else {
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login failed due to ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}