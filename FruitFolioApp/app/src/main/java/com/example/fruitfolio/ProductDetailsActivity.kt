package com.example.fruitfolio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitfolio.databinding.ProductDetailsBinding
import com.example.fruitfolio.retrofit.GradeRequest
import com.example.fruitfolio.retrofit.MainApi
import com.example.fruitfolio.retrofit.Product
import com.example.fruitfolio.retrofit.RetrofitService
import com.example.fruitfolio.retrofit.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ProductDetailsBinding

    private var userResponse: UserResponse? = null
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userResponse = intent.getSerializableExtra("userResponse") as? UserResponse
        product = intent.getSerializableExtra("product") as? Product

        binding.textViewProductSort.text = product?.sort ?: "Product sort"
        binding.textViewProductType.text = product?.type ?: "Product type"
        binding.textViewMeanGrade.text = product?.let { String.format("%.1f", it.meanGrade) }

        if (userResponse != null) {
            binding.imageViewHome.setOnClickListener {
                home(userResponse!!)
            }
        } else {
            auth()
        }

        if (product != null) {
            binding.star1.setOnClickListener {
                createGrade(product!!.id, 1)
                binding.star1.setBackgroundResource(R.drawable.star_fill)
                binding.star2.setBackgroundResource(R.drawable.star_blank)
                binding.star3.setBackgroundResource(R.drawable.star_blank)
                binding.star4.setBackgroundResource(R.drawable.star_blank)
                binding.star5.setBackgroundResource(R.drawable.star_blank)
            }
            binding.star2.setOnClickListener {
                createGrade(product!!.id, 2)
                binding.star1.setBackgroundResource(R.drawable.star_fill)
                binding.star2.setBackgroundResource(R.drawable.star_fill)
                binding.star3.setBackgroundResource(R.drawable.star_blank)
                binding.star4.setBackgroundResource(R.drawable.star_blank)
                binding.star5.setBackgroundResource(R.drawable.star_blank)
            }
            binding.star3.setOnClickListener {
                createGrade(product!!.id, 3)
                binding.star1.setBackgroundResource(R.drawable.star_fill)
                binding.star2.setBackgroundResource(R.drawable.star_fill)
                binding.star3.setBackgroundResource(R.drawable.star_fill)
                binding.star4.setBackgroundResource(R.drawable.star_blank)
                binding.star5.setBackgroundResource(R.drawable.star_blank)
            }
            binding.star4.setOnClickListener {
                createGrade(product!!.id, 4)
                binding.star1.setBackgroundResource(R.drawable.star_fill)
                binding.star2.setBackgroundResource(R.drawable.star_fill)
                binding.star3.setBackgroundResource(R.drawable.star_fill)
                binding.star4.setBackgroundResource(R.drawable.star_fill)
                binding.star5.setBackgroundResource(R.drawable.star_blank)
            }
            binding.star5.setOnClickListener {
                createGrade(product!!.id, 5)
                binding.star1.setBackgroundResource(R.drawable.star_fill)
                binding.star2.setBackgroundResource(R.drawable.star_fill)
                binding.star3.setBackgroundResource(R.drawable.star_fill)
                binding.star4.setBackgroundResource(R.drawable.star_fill)
                binding.star5.setBackgroundResource(R.drawable.star_fill)
            }
        }
    }

    private fun createGrade(productSortId: Int, grade: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val gradeRequest = GradeRequest(productSortId, grade)
                Log.d("ProductDetailsActivity", "$gradeRequest")
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .createGrade(gradeRequest, "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    val meanGrade = response.body()
                    Log.d("ProductDetailsActivity", "$meanGrade")
                    binding.textViewMeanGrade.text = String.format("%.1f", meanGrade)
                } else {
                    auth()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun home(userResponse: UserResponse) {
        val intent = Intent(this@ProductDetailsActivity,
            ProductClassesActivity::class.java)
        intent.putExtra("userResponse", userResponse)
        startActivity(intent)
        finish()
    }

    private fun auth() {
        val intent = Intent(this@ProductDetailsActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}