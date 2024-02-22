package com.example.fruitfolio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitfolio.databinding.ProductClassesBinding
import com.example.fruitfolio.retrofit.MainApi
import com.example.fruitfolio.retrofit.RetrofitService
import com.example.fruitfolio.retrofit.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductClassesActivity : AppCompatActivity() {

    private lateinit var binding: ProductClassesBinding

    private var userResponse: UserResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductClassesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userResponse = intent.getSerializableExtra("userResponse") as? UserResponse

        binding.fruitsTextView.setOnClickListener { getProducts(ProductClasses.Fruit) }
    }

    private fun getProducts(productClass: ProductClasses) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .getProducts(productClass.name, "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    val productList = response.body()
                    Log.d("ProductClassesActivity", "Products: $productList")
                    productList?.let {
                        val intent = Intent(this@ProductClassesActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    binding.fruitFolioTextView.text = "Error"
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
        startActivity(Intent(this@ProductClassesActivity, MainActivity::class.java))
        finish()
    }
}