package com.example.otp_generator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.otp_generator.databinding.ActivityMainBinding
import com.example.otp_generator.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class ActivityOtp : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToLogin.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        var x=binding.phoneNo
        val progressbar=binding.progressbar
        lateinit var generatedotp: String


        binding.generateOtp.setOnClickListener {
            if(!x.text.toString().trim().isEmpty() && (x.text.toString().trim()).length==10){

//                progressbar.visibility=View.VISIBLE
//                x.visibility=View.INVISIBLE

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + x.text.toString(),
                    60,
                    TimeUnit.SECONDS,
                    this,
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            // Called when verification is done automatically (e.g., SMS code auto-retrieval).
                            // You can use credential to sign in the user.
//                            progressbar.visibility=View.GONE
//                            x.visibility=View.VISIBLE
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            // Called when verification fails (e.g., wrong phone number).
                            // Handle the error.
//                            progressbar.visibility=View.GONE
//                            x.visibility=View.VISIBLE
                            Toast.makeText(this@ActivityOtp,e.message,Toast.LENGTH_LONG).show()
                        }

                        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                            // Called when the verification code is successfully sent to the provided phone number.
                            // You can save the verification ID and resend token to use later.
//                            progressbar.visibility=View.GONE
//                            x.visibility=View.VISIBLE

                            generatedotp=verificationId
                            val y=binding.verifyOtp.text.toString()

                            if(y.trim().isNotEmpty()){
                                val phoneAuthCredential: PhoneAuthCredential = PhoneAuthProvider.getCredential(generatedotp, y)
                                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // Sign-in successful
//                                            progressbar.visibility=View.GONE
//                                            x.visibility=View.VISIBLE
                                            val intent = Intent(this@ActivityOtp,ActivityContent::class.java)
                                            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
                                            // Continue with the authenticated user
                                        } else {
                                            // If sign-in fails, display a message to the user.
                                            // You can use task.exception to get more details about the failure.
                                            Toast.makeText(this@ActivityOtp,"Wrong OTP, Try Again",Toast.LENGTH_LONG).show()
                                        }
                                    }
                            }
                            else{
                                Toast.makeText(this@ActivityOtp,"Enter Correct NO.",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                )
            }
            else{
                Toast.makeText(this@ActivityOtp, "Please Enter Valid Number", Toast.LENGTH_LONG).show()
            }
        }

//        if(!generatedotp.isNullOrEmpty() && y.toString()==generatedotp){
//            val intent=Intent(this,ActivityContent::class.java)
//            startActivity(intent)
//        }

    }
}