package com.example.fruitfolio

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitfolio.databinding.MyCommentItemBinding
import com.example.fruitfolio.retrofit.MyCommentResponse
import com.example.fruitfolio.retrofit.Product
import java.text.SimpleDateFormat
import java.util.Locale

class MyCommentsAdapter(private var myCommentsList: List<MyCommentResponse>, private val onItemClick: (MyCommentResponse) -> Unit) :
    RecyclerView.Adapter<MyCommentsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: MyCommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: MyCommentResponse) {
            binding.apply {
                textViewComment.text = comment.text
                val formattedDate = formatDateTime(comment.createDate)
                textViewDateTime.text = formattedDate

                deleteImageView.setOnClickListener {
                    onItemClick(comment)
                }
            }
        }
    }

    private fun formatDateTime(dateTimeString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateTimeString)
        return outputFormat.format(date!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MyCommentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = myCommentsList[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return myCommentsList.size
    }

    fun updateData(newComments: List<MyCommentResponse>) {
        myCommentsList = newComments
        notifyDataSetChanged()
    }
}