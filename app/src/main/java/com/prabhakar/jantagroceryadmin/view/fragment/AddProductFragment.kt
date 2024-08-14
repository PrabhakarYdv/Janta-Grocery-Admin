package com.prabhakar.jantagroceryadmin.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.prabhakar.jantagroceryadmin.Constraints
import com.prabhakar.jantagroceryadmin.R
import com.prabhakar.jantagroceryadmin.Utils
import com.prabhakar.jantagroceryadmin.adapter.SelectedImagesAdapter
import com.prabhakar.jantagroceryadmin.databinding.FragmentAddProductBinding
import com.prabhakar.jantagroceryadmin.models.ProductModel
import com.prabhakar.jantagroceryadmin.view.activity.AdminActivity
import com.prabhakar.jantagroceryadmin.viewmodels.AdminViewModel
import kotlinx.coroutines.launch

class AddProductFragment : Fragment() {
    private lateinit var binding: FragmentAddProductBinding
    private val imagesUri: ArrayList<Uri> = arrayListOf()
    private val adminViewModel: AdminViewModel by viewModels()

    private val selectedImage =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            val fiveImages = it.take(5)
            imagesUri.clear()
            imagesUri.addAll(fiveImages)

            binding.productImagesRecyclerView.adapter = SelectedImagesAdapter(imagesUri)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(layoutInflater)

        setAutoCompleteTextView()
        imageSelect()
        binding.btnAddProduct.setOnClickListener{
            addProduct()
        }

        return binding.root
    }

    private fun setAutoCompleteTextView() {
        val category =ArrayAdapter(requireContext(), R.layout.show_list, Constraints.allProductsCategory)
        val unit = ArrayAdapter(requireContext(), R.layout.show_list, Constraints.allUnits)
        val type = ArrayAdapter(requireContext(), R.layout.show_list, Constraints.allProductType)

        binding.apply {
            productCategory.setAdapter(category)
            productUnit.setAdapter(unit)
            productType.setAdapter(type)
        }
    }

    private fun imageSelect() {
        binding.btnSelectImage.setOnClickListener {
            selectedImage.launch("image/")
        }
    }

    private fun addProduct() {
        Utils.showDialog(requireContext(), "Uploading Product")
        val productName = binding.productName.text.toString()
        val productQuantity = binding.productQuantity.text.toString()
        val productUnit = binding.productUnit.text.toString()
        val productPrice = binding.productPrice.text.toString()
        val productStock = binding.productStock.text.toString()
        val productCategory = binding.productCategory.text.toString()
        val productType = binding.productType.text.toString()

        if (productName.isEmpty() ||
            productQuantity.isEmpty() ||
            productUnit.isEmpty() ||
            productPrice.isEmpty() ||
            productStock.isEmpty() ||
            productCategory.isEmpty() ||
            productType.isEmpty()
        ) {
            Utils.hideDialog()
            Utils.showToast(requireContext(), "All fields are required")
        } else if (imagesUri.isEmpty()) {
            Utils.hideDialog()
            Utils.showToast(requireContext(), "Uploads Some Images")
        } else {
            val productModel = ProductModel(
                productName = productName,
                productQuantity = productQuantity.toInt(),
                productUnit = productUnit,
                productPrice = productPrice.toInt(),
                productStock = productStock.toInt(),
                productCategory = productCategory,
                productType = productType,
                itemCount = 0,
                adminUid = Utils.getUId(),
                productRandomId = Utils.getRandomId()
            )
            saveImage(productModel)
        }
    }

    private fun saveImage(productModel: ProductModel) {
        adminViewModel.saveImageInDB(imagesUri)
        lifecycleScope.launch {
            adminViewModel.isImageUploaded.collect {
                if (it) {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(), "Images Saved")
                }
                getUrls(productModel)
            }
        }
    }

    private fun getUrls(productModel: ProductModel) {
        Utils.showDialog(requireContext(), "Publishing Product")
        lifecycleScope.launch {
            adminViewModel.downloadedUrls.collect {
                productModel.productImageUris = it
                saveProduct(productModel)
            }
        }
    }

    private fun saveProduct(productModel: ProductModel) {
        adminViewModel.saveProduct(productModel)
        lifecycleScope.launch {
            adminViewModel.isProductSaved.collect {
                if (it) {
                    Utils.hideDialog()
                    startActivity(Intent(requireActivity(), AdminActivity::class.java))
                    Utils.showToast(requireContext(), "Product Saved")
                }
            }
        }
    }

}