package com.example.mmcash.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mmcash.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import models.User


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var actionBar: ActionBar


    private lateinit var firebaseAuth: FirebaseAuth

    var username = ""
    var fullname = ""
    var email = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            validateData()
        }
    }

    private fun putUserInDatabase(task: Task<AuthResult>) {
        val user = User(username, fullname, email)
        if (task.isSuccessful) {
            FirebaseDatabase.getInstance().getReference("Users")
                .child(username)
                .setValue(user)
        } else {
            Toast.makeText(
                this,
                "Failed to register! Try again!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateData() {

        email = binding.tvEmail.text.toString().trim()
        password = binding.passwordSignup.text.toString().trim()
        fullname = binding.fullName.text.toString().trim()
        username = binding.usernameSignup.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            binding.tvEmail.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordSignup.error = "Please enter password"
        } else if (password.length < 6) {
            binding.passwordSignup.error = "Password must be at least 6 characters long"
        } else if (TextUtils.isEmpty(fullname)) {
            binding.fullName.error = "Please enter full name"
        } else if (TextUtils.isEmpty(username)) {
            binding.usernameSignup.error = "Please enter username"
        } else {
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Signup failed due to ${it.message}", Toast.LENGTH_SHORT).show()
            }.addOnCompleteListener {
                putUserInDatabase(it)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}