package com.prabhakar.jantagroceryadmin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.jantagroceryadmin.databinding.CategoryItemLayoutBinding
import com.prabhakar.jantagroceryadmin.models.CategoryModel
import com.prabhakar.jantagroceryadmin.viewholder.CategoryViewHolder

class CategoryAdapter(
    private val categoryList: ArrayList<CategoryModel>,
    val onClickCategory: (CategoryModel) -> Unit
) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val model = categoryList[position]
        holder.setData(model)
        holder.itemView.setOnClickListener {
            onClickCategory(model)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}