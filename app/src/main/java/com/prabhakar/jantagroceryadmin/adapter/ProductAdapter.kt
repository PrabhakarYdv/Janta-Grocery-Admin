package com.prabhakar.jantagroceryadmin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.models.SlideModel
import com.prabhakar.jantagroceryadmin.databinding.ProductItemLayoutBinding
import com.prabhakar.jantagroceryadmin.filter.FilterProduct
import com.prabhakar.jantagroceryadmin.models.ProductModel
import com.prabhakar.jantagroceryadmin.viewholder.ProductViewHolder

class ProductAdapter(val onClickEditBtn: (ProductModel) -> Unit) :
    RecyclerView.Adapter<ProductViewHolder>(), Filterable {


    val diffUtil = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.productRandomId == newItem.productRandomId
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productModel = differ.currentList[position]
        holder.setData(productModel)
        holder.binding.apply {
            val imageList = ArrayList<SlideModel>()
            val productImage = productModel.productImageUris

            for (i in 0 until productImage?.size!!) {
                imageList.add(SlideModel(productModel.productImageUris!![i].toString()))
            }

            imageSlider.setImageList(imageList)
        }

        holder.binding.edit.setOnClickListener {
            onClickEditBtn(productModel)
        }


    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val filter: FilterProduct? = null
    var originalList = ArrayList<ProductModel>()

    override fun getFilter(): Filter {
        if (filter == null) {
            return FilterProduct(this, originalList)
        }
        return filter
    }

}