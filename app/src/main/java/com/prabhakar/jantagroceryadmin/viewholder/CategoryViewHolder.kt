package com.prabhakar.jantagroceryadmin.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.jantagroceryadmin.databinding.CategoryItemLayoutBinding
import com.prabhakar.jantagroceryadmin.models.CategoryModel

class CategoryViewHolder(private val binding: CategoryItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(model: CategoryModel) {
        binding.apply {
            categoryImage.setImageResource(model.image)
            categoryName.text = model.category
        }
    }
}