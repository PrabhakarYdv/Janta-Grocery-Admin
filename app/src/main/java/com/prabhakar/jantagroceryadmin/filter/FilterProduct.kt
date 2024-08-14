package com.prabhakar.jantagroceryadmin.filter

import android.widget.Filter
import com.prabhakar.jantagroceryadmin.adapter.ProductAdapter
import com.prabhakar.jantagroceryadmin.models.ProductModel
import java.util.Locale

class FilterProduct(
    private val adapter: ProductAdapter,
    private val productList: ArrayList<ProductModel>
) : Filter() {
    override fun performFiltering(constarint: CharSequence?): FilterResults {
        val result = FilterResults()

        if (constarint.isNullOrEmpty()) {
            val filterList = ArrayList<ProductModel>()
            val query = constarint.toString().trim().lowercase(Locale.getDefault()).split(" ")
            for (products in productList) {
                if (query.any {
                        products.productName?.lowercase(Locale.getDefault())
                            ?.contains(it) == true ||
                                products.productCategory?.lowercase(Locale.getDefault())
                                    ?.contains(it) == true ||
                                products.productType?.lowercase(Locale.getDefault())
                                    ?.contains(it) == true
                    }) {
                    filterList.add(products)


                }
            }
            result.values = filterList
            result.count = filterList.size

        } else {
            result.values = productList
            result.count = productList.size
        }



        return result
    }

    override fun publishResults(p0: CharSequence?, result: FilterResults?) {
        adapter.differ.submitList(result?.values as ArrayList<ProductModel>?)
    }
}