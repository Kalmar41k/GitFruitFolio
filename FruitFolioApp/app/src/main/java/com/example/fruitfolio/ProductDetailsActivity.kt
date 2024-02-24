package com.example.fruitfolio

import android.content.Intent
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruitfolio.databinding.ProductDetailsBinding
import com.example.fruitfolio.retrofit.CommentRequest
import com.example.fruitfolio.retrofit.CommentResponse
import com.example.fruitfolio.retrofit.GradeRequest
import com.example.fruitfolio.retrofit.MainApi
import com.example.fruitfolio.retrofit.MyCommentRequest
import com.example.fruitfolio.retrofit.MyCommentResponse
import com.example.fruitfolio.retrofit.Product
import com.example.fruitfolio.retrofit.RetrofitService
import com.example.fruitfolio.retrofit.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ProductDetailsBinding

    private var userResponse: UserResponse? = null
    private var product: Product? = null
    private var comments: List<CommentResponse>? = null
    private var myComments: List<MyCommentResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userResponse = intent.getSerializableExtra("userResponse") as? UserResponse
        product = intent.getSerializableExtra("product") as? Product

        binding.textViewProductSort.text = product?.sort ?: "Product sort"
        binding.textViewProductType.text = product?.type ?: "Product type"
        binding.textViewMeanGrade.text = product?.let { String.format("%.1f", it.meanGrade) }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.recyclerViewComments.layoutManager = layoutManager
        getComments()

        binding.textViewMyComments.setOnClickListener{ getMyComments() }

        binding.textViewComments.setOnClickListener{ getComments() }

        binding.sendImageView.setOnClickListener {
            val checkText = binding.writeCommentEditText.text.toString()
            if (checkText.isEmpty()) {
                binding.writeCommentEditText.error = "Write some text!"
            }
            else {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                addComment()
            }
        }

        binding.writeCommentEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val checkText = binding.writeCommentEditText.text.toString()
                if (checkText.isEmpty()) {
                    binding.writeCommentEditText.error = "Write some text!"
                }
                else {
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    addComment()
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

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

    private fun getComments() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .getComments(product!!.id, "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    comments = response.body()
                    runOnUiThread {
                        binding.recyclerViewComments.adapter = CommentsAdapter(comments!!)
                    }
                } else {
                    auth()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getMyComments() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val myCommentRequest = MyCommentRequest(product!!.id)
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .getMyComments(myCommentRequest, "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    myComments = response.body()
                    runOnUiThread {
                        binding.recyclerViewComments.adapter = MyCommentsAdapter(myComments!!) {
                            comment -> deleteMyComment(comment)
                        }
                    }
                } else {
                    auth()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun addComment() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val text = binding.writeCommentEditText.text.toString()
                val commentRequest = CommentRequest(product!!.id, text)
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .createComment(commentRequest, "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    runOnUiThread {
                        getComments()
                        binding.writeCommentEditText.text.clear()
                    }
                } else {
                    auth()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteMyComment(comment: MyCommentResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .deleteMyComment(comment.id, "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    runOnUiThread {
                        getMyComments()
                    }
                } else {
                    auth()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createGrade(productSortId: Int, grade: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val gradeRequest = GradeRequest(productSortId, grade)
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