package com.example.fruitfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fruitfolio.databinding.ActivityMainBinding
import com.example.fruitfolio.retrofit.MainApi
import com.example.fruitfolio.retrofit.RegisterRequest
import com.example.fruitfolio.retrofit.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameText.text.toString()
            val lastName = binding.lastNameText.text.toString()
            val email = binding.emailText.text.toString()
            val password = binding.textPassword.text.toString()

            val registerRequest = RegisterRequest(firstName, lastName, email, password)

            registerUser(registerRequest)

        }

        binding.signInButton.setOnClickListener {signIn()}
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java).register(registerRequest)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("MainActivity", "UserResponse: $userResponse")
                    userResponse?.let {
                        val intent = Intent(this@MainActivity,
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
        val intent = Intent(this@MainActivity,
            SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}