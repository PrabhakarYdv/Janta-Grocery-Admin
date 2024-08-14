package com.prabhakar.jantagroceryadmin.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.jantagroceryadmin.Constraints
import com.prabhakar.jantagroceryadmin.R
import com.prabhakar.jantagroceryadmin.Utils
import com.prabhakar.jantagroceryadmin.adapter.CategoryAdapter
import com.prabhakar.jantagroceryadmin.adapter.ProductAdapter
import com.prabhakar.jantagroceryadmin.databinding.EditProductLayoutBinding
import com.prabhakar.jantagroceryadmin.databinding.FragmentHomeBinding
import com.prabhakar.jantagroceryadmin.models.CategoryModel
import com.prabhakar.jantagroceryadmin.models.ProductModel
import com.prabhakar.jantagroceryadmin.viewmodels.AdminViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val categoryList = arrayListOf<CategoryModel>()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        buildCategoryList()
        setCategoryRecyclerView()
        getAllProduct("All")
        return binding.root
    }

    private fun buildCategoryList() {
        categoryList.add(CategoryModel(R.drawable.cold_and_juices, "All"))
        categoryList.add(CategoryModel(R.drawable.vegetable, "Vegetables & Fruits"))
        categoryList.add(CategoryModel(R.drawable.dairy_breakfast, "Dairy & Breakfast"))
        categoryList.add(CategoryModel(R.drawable.munchies, "Munchies"))
        categoryList.add(CategoryModel(R.drawable.cold_and_juices, "Cold Drinks & Juices"))
        categoryList.add(CategoryModel(R.drawable.instant, "Instant & Frozen Food"))
        categoryList.add(CategoryModel(R.drawable.tea, "Tea, Coffee & Health Drinks"))
        categoryList.add(CategoryModel(R.drawable.bakery_biscuits, "Bakery & Biscuits"))
        categoryList.add(CategoryModel(R.drawable.sweet_tooth, "Sweet Tooth"))
        categoryList.add(CategoryModel(R.drawable.aata_rice, "Aata, Rice & Dal"))
        categoryList.add(CategoryModel(R.drawable.masala, "Dry Fruits, Masala & Oil"))
        categoryList.add(CategoryModel(R.drawable.sauce_spreads, "Sauces & Spreads"))
        categoryList.add(CategoryModel(R.drawable.chicken_meat, "Chicken Meat & FIsh"))
        categoryList.add(CategoryModel(R.drawable.pen_corner, "Pen Corner"))
        categoryList.add(CategoryModel(R.drawable.organic_premium, "Organic & Premium"))
        categoryList.add(CategoryModel(R.drawable.baby, "Baby Care"))
        categoryList.add(CategoryModel(R.drawable.pharma_wellness, "Pharma & Wellness"))
        categoryList.add(CategoryModel(R.drawable.cleaning, "Cleaning Essential"))
        categoryList.add(CategoryModel(R.drawable.home_office, "Home & Office"))
        categoryList.add(CategoryModel(R.drawable.personal_care, "Personal Care"))
        categoryList.add(CategoryModel(R.drawable.pet_care, "Pet Care"))
    }

    private fun setCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(categoryList, ::onClickCategory)
        val orientation: Int = RecyclerView.HORIZONTAL;
        binding.categoriesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), orientation, false)
        binding.categoriesRecyclerView.adapter = categoryAdapter
    }

    private fun getAllProduct(category: String) {
        binding.shimmerEffect.visibility = View.VISIBLE
        binding.productsRecyclerView.visibility = View.GONE
        lifecycleScope.launch {
            adminViewModel.fetchAllProduct(category).collect {
                if (it.isEmpty()) {
                    binding.productsRecyclerView.visibility = View.GONE
                    binding.relativeLayout.visibility = View.VISIBLE
                } else {
                    binding.productsRecyclerView.visibility = View.VISIBLE
                    binding.relativeLayout.visibility = View.GONE
                }
                productAdapter = ProductAdapter(::onClickEditBtn)
                binding.productsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.productsRecyclerView.adapter = productAdapter
                productAdapter.differ.submitList(it)
                binding.shimmerEffect.visibility = View.GONE
                binding.productsRecyclerView.visibility = View.VISIBLE
            }
        }

    }

    private fun onClickCategory(categoryModel: CategoryModel) {
        getAllProduct(categoryModel.category)
    }

    private fun onClickEditBtn(productModel: ProductModel) {
        val editProduct = EditProductLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        editProduct.apply {
            productName.setText(productModel.productName)
            productQuantity.setText(productModel.productQuantity.toString())
            productUnit.setText(productModel.productUnit)
            productPrice.setText(productModel.productPrice.toString())
            productStock.setText(productModel.productStock.toString())
            productCategory.setText(productModel.productCategory)
            productType.setText(productModel.productType)
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(editProduct.root)
            .create()
        dialog.show()

        editProduct.btnEdit.setOnClickListener {
            editProduct.apply {
                productName.isEnabled = true
                productQuantity.isEnabled = true
                productUnit.isEnabled = true
                productPrice.isEnabled = true
                productStock.isEnabled = true
                productCategory.isEnabled = true
                productType.isEnabled = true
            }
        }
        setAutoCompleteTextView(editProduct)

        editProduct.btnSave.setOnClickListener {

            lifecycleScope.launch {
                productModel.productName = editProduct.productName.toString()
                productModel.productQuantity = editProduct.productQuantity.toString().toInt()
                productModel.productUnit = editProduct.productUnit.toString()
                productModel.productPrice = editProduct.productPrice.toString().toInt()
                productModel.productStock = editProduct.productStock.toString().toInt()
                productModel.productCategory = editProduct.productCategory.toString()
                productModel.productType = editProduct.productType.toString()
                adminViewModel.updateProduct(productModel)
            }
            Utils.showToast(requireContext(),"Product has been Updated")
            dialog.dismiss()
        }
    }

    private fun setAutoCompleteTextView(editProduct: EditProductLayoutBinding) {
        val category =
            ArrayAdapter(requireContext(), R.layout.show_list, Constraints.allProductsCategory)
        val unit = ArrayAdapter(requireContext(), R.layout.show_list, Constraints.allUnits)
        val type = ArrayAdapter(requireContext(), R.layout.show_list, Constraints.allProductType)

        editProduct.apply {
            productCategory.setAdapter(category)
            productUnit.setAdapter(unit)
            productType.setAdapter(type)
        }
    }
}