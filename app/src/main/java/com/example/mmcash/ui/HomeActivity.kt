package com.example.mmcash.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mmcash.R
import com.example.mmcash.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import custom.ChooseAccountDialog

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var actionBar: ActionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        actionBar = supportActionBar!!
        actionBar.title = "Home"



        val animation = AnimationUtils.loadAnimation(this, R.anim.circle_explosion_anim).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }

        val dialog = ChooseAccountDialog(this)

        binding.btnOption1.setOnClickListener {
            dialog.show()
            dialog.setOnResultListener {
                if (it){
                    startActivity(Intent(this, Balance::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, Balance::class.java))
                    finish()
                }

            }

        }

    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            var numb = 1
        }
        else{
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }
}