package com.example.fruitfolio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitfolio.databinding.SignInBinding
import com.example.fruitfolio.retrofit.AuthenticateRequest
import com.example.fruitfolio.retrofit.MainApi
import com.example.fruitfolio.retrofit.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class SignInActivity : AppCompatActivity() {

    lateinit var binding: SignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.textPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val authenticateRequest = AuthenticateRequest(email, password)
                authUser(authenticateRequest)
            } else {
                if (email.isEmpty()) {
                    binding.emailText.error = "All fields must be filled!"
                }
                if (password.isEmpty()) {
                    binding.textPassword.error = "All fields must be filled!"
                }
            }
        }

        binding.signUpButton.setOnClickListener {signUp()}
    }

    private fun authUser(authenticateRequest: AuthenticateRequest) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java).authenticate(authenticateRequest)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("SignInActivity", "UserResponse: $userResponse")
                    userResponse?.let {
                        val intent = Intent(this@SignInActivity,
                            ProductClassesActivity::class.java)
                        intent.putExtra("userResponse", userResponse)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                        runOnUiThread {
                            binding.emailText.error = "Wrong email or password!"
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun signUp() {
        val intent = Intent(this@SignInActivity,
            RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}