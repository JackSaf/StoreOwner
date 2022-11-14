package com.jacksafblaze.storeowner.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacksafblaze.storeowner.databinding.CategoryListItemLayoutBinding
import com.jacksafblaze.storeowner.domain.model.Category


class CategoryAdapter(private val onItemClickListener: (String, String) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryListItemLayoutBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setList(list: List<Category>) {
        differ.submitList(list)
    }

    inner class CategoryViewHolder(private val binding: CategoryListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.categoryCardView.setOnClickListener{
                onItemClickListener.invoke(category.id, category.name)
            }
            binding.name.text = category.name
            Glide.with(binding.root).load(category.imageUri).into(binding.image)
        }
    }
}