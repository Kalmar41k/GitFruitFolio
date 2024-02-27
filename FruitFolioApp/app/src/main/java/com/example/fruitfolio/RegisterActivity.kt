package com.example.fruitfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fruitfolio.databinding.RegisterBinding
import com.example.fruitfolio.retrofit.MainApi
import com.example.fruitfolio.retrofit.RegisterRequest
import com.example.fruitfolio.retrofit.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameText.text.toString()
            val lastName = binding.lastNameText.text.toString()
            val email = binding.emailText.text.toString()
            val password = binding.textPassword.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val registerRequest = RegisterRequest(firstName, lastName, email, password)
                registerUser(registerRequest)
            } else {
                if (firstName.isEmpty()) {
                    binding.firstNameText.error = "All fields must be filled!"
                }
                if (lastName.isEmpty()) {
                    binding.lastNameText.error = "All fields must be filled!"
                }
                if (email.isEmpty()) {
                    binding.emailText.error = "All fields must be filled!"
                }
                if (password.isEmpty()) {
                    binding.textPassword.error = "All fields must be filled!"
                }
            }
        }
        binding.signInButton.setOnClickListener {signIn()}
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java).register(registerRequest)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("RegisterActivity", "UserResponse: $userResponse")
                    userResponse?.let {
                        val intent = Intent(this@RegisterActivity,
                            ProductClassesActivity::class.java)
                        intent.putExtra("userResponse", userResponse)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    if (response.code() == HttpURLConnection.HTTP_CONFLICT) {
                        runOnUiThread {
                            binding.emailText.error = "This email is already exist!"
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun signIn() {
        val intent = Intent(this@RegisterActivity,
            SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}