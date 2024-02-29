package com.example.fruitfolio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitfolio.retrofit.Product
import com.example.fruitfolio.databinding.ItemLayoutBinding

class ProductsAdapter(private var productsList: List<Product>, private val onItemClick: (Product) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        productsList = productsList.sortedBy { it.meanGrade }
        val product = productsList[position]
        holder.binding.textViewProductSort.text = product.sort
        holder.binding.textViewProductType.text = product.type
        holder.binding.textViewRating.text = String.format("%.1f", product.meanGrade)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}