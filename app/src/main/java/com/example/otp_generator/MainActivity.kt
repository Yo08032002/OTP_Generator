package com.example.otp_generator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.otp_generator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener {
            val intent=Intent(this,ActivityOtp::class.java)
            startActivity(intent)
        }

        binding.forgetPassword.setOnClickListener{
            val intent=Intent(this,ActivityOtp::class.java)
            startActivity(intent)
        }

    }
}