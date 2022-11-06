package com.jacksafblaze.storeowner.presentation.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jacksafblaze.storeowner.databinding.ProductListItemLayoutBinding
import com.jacksafblaze.storeowner.domain.model.Product

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val callback = object : ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductListItemLayoutBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setList(list: List<Product>){
        differ.submitList(list)
    }

    inner class ProductViewHolder(private val binding: ProductListItemLayoutBinding): ViewHolder(binding.root){
        fun bind(product: Product){
            Glide.with(binding.root).load(product.imageUri).into(binding.image)
            binding.name.text = product.imageUri
        }
    }
}