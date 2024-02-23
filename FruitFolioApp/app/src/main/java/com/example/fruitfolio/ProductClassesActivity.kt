package com.example.fruitfolio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitfolio.databinding.ProductClassesBinding
import com.example.fruitfolio.retrofit.MainApi
import com.example.fruitfolio.retrofit.RetrofitService
import com.example.fruitfolio.retrofit.UserResponse
import com.google.gson.Gson
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
        binding.vegetablesTextView.setOnClickListener { getProducts(ProductClasses.Vegetable) }
        binding.berriesTextView.setOnClickListener { getProducts(ProductClasses.Berry) }
    }

    private fun getProducts(productClass: ProductClasses) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .getProducts(productClass.name, "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    val productsList = response.body()
                    Log.d("ProductClassesActivity", "Products: $productsList")
                    productsList?.let {
                        val productsJson = Gson().toJson(productsList)
                        val intent = Intent(this@ProductClassesActivity, ProductsActivity::class.java)
                        intent.putExtra("userResponse", userResponse)
                        intent.putExtra("productsJson", productsJson)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    auth()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun auth() {
        val intent = Intent(this@ProductClassesActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}

