package com.example.fruitfolio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitfolio.databinding.CommentItemBinding
import com.example.fruitfolio.retrofit.CommentResponse
import java.text.SimpleDateFormat
import java.util.Locale

class CommentsAdapter(private var commentsList: List<CommentResponse>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentResponse) {
            binding.apply {
                val username = "${comment.firstname} ${comment.lastname}"
                textViewUsername.text = username
                textViewComment.text = comment.text

                val formattedDate = formatDateTime(comment.createDate)
                textViewDateTime.text = formattedDate
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
        val binding = CommentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentsList[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

}