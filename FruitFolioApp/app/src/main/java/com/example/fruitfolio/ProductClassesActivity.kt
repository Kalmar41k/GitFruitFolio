package com.example.fruitfolio

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitfolio.databinding.ProductClassesBinding
import com.example.fruitfolio.retrofit.UserResponse

class ProductClassesActivity : AppCompatActivity() {

    private lateinit var binding: ProductClassesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductClassesBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val userResponse = intent.getSerializableExtra("userResponse") as? UserResponse

        binding.fruitsTextView.setOnClickListener { onButtonPressed() }
    }

    private fun onButtonPressed() {
        startActivity(Intent(this@ProductClassesActivity, MainActivity::class.java))
        finish()
    }
}