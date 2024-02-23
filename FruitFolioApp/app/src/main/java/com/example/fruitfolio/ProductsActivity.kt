package com.example.fruitfolio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruitfolio.databinding.ProductsBinding
import com.example.fruitfolio.retrofit.Product
import com.example.fruitfolio.retrofit.UserResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ProductsBinding

    private var userResponse: UserResponse? = null
    private var productsList: List<Product>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userResponse = intent.getSerializableExtra("userResponse") as? UserResponse
        val productsJson = intent.getStringExtra("productsJson")

        val productListType = object : TypeToken<List<Product>>() {}.type
        productsList = Gson().fromJson(productsJson, productListType)
        Log.d("ProductsActivity", "$productsList")
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
        if (productsList != null) {
            binding.recyclerViewProducts.adapter = ProductsAdapter(productsList!!) {
                product -> navigateToProductDetails(product)
            }
            val itemCount = binding.recyclerViewProducts.adapter?.itemCount ?: 0
            Log.d("ProductsActivity", "Item count: $itemCount")
        }

        if (userResponse != null) {
            binding.imageViewHome.setOnClickListener {
                home(userResponse!!)
            }
        } else {
            auth()
        }
    }

    private fun navigateToProductDetails(product: Product) {
        val intent = Intent(this@ProductsActivity, ProductDetailsActivity::class.java)
        intent.putExtra("userResponse", userResponse)
        intent.putExtra("product", product)
        startActivity(intent)
        finish()
    }

    private fun home(userResponse: UserResponse) {
        val intent = Intent(this@ProductsActivity,
            ProductClassesActivity::class.java)
        intent.putExtra("userResponse", userResponse)
        startActivity(intent)
        finish()
    }

    private fun auth() {
        val intent = Intent(this@ProductsActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}