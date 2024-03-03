package com.example.fruitfolio

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitfolio.databinding.ProductClassesBinding
import com.example.fruitfolio.retrofit.MainApi
import com.example.fruitfolio.retrofit.NeuralNetworkRetrofitService
import com.example.fruitfolio.retrofit.responses.PredictResponse
import com.example.fruitfolio.retrofit.RetrofitService
import com.example.fruitfolio.retrofit.responses.UserResponse
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.HttpURLConnection

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

        binding.logOutView.setOnClickListener { auth() }

        binding.cameraImageView.setOnClickListener {
            ImagePicker.with(this@ProductClassesActivity)
                .crop()
                .compress(1024)
                .maxResultSize(512,512)
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                val imageFile = uriToFile(this@ProductClassesActivity, it)
                uploadImage(imageFile)
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage(imageFile: File?) {
        imageFile?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData(
                        "image", imageFile.name, requestFile)
                    val response = NeuralNetworkRetrofitService.retrofit.create(MainApi::class.java)
                        .uploadImage(body)
                    if (response.isSuccessful) {
                        val predict = response.body()
                        if (predict!!.description == "Product did not found!") {
                            runOnUiThread {
                                AlertDialog.Builder(this@ProductClassesActivity)
                                    .setTitle("Product Not Found")
                                    .setMessage("The product you are looking for was not found.")
                                    .setPositiveButton("OK") { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    .show()
                            }
                        }
                        findProductByDescription(predict)
                        Log.d("ProductClassesActivity", "Predict: $predict")
                    } else {
                        runOnUiThread {
                            AlertDialog.Builder(this@ProductClassesActivity)
                                .setTitle("Cannot upload Image")
                                .setMessage("Cannot upload Image. Please try again!")
                                .setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun findProductByDescription(predictResponse: PredictResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .byDescription(predictResponse, "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    val product = response.body()
                    product?.let {
                        val intent = Intent(
                            this@ProductClassesActivity,
                            ProductDetailsActivity::class.java)
                        intent.putExtra("userResponse", userResponse)
                        intent.putExtra("product", product)
                        startActivity(intent)
                        finish()
                    }
                } else if (response.code() == HttpURLConnection.HTTP_NOT_FOUND){
                    runOnUiThread {
                        AlertDialog.Builder(this@ProductClassesActivity)
                            .setTitle("Product Not Found")
                            .setMessage("The product you are looking for was not found.")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                    Log.d("ProductClassesActivity", "Product not found!")
                } else {
                    auth()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.cacheDir, "temp_file")
            inputStream.use { input ->
                file.outputStream().use { output ->
                    input?.copyTo(output)
                }
            }
            Log.d("ProductClassesActivity", "File path: ${file.absolutePath}")
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getProducts(productClass: ProductClasses) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitService.retrofit.create(MainApi::class.java)
                    .getProducts(productClass.name,
                        "Bearer ${userResponse?.accessToken}")
                if (response.isSuccessful) {
                    val productsList = response.body()
                    Log.d("ProductClassesActivity", "Products: $productsList")
                    productsList?.let {
                        val productsJson = Gson().toJson(productsList)
                        val intent = Intent(
                            this@ProductClassesActivity,
                            ProductsActivity::class.java)
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

