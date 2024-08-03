package com.prabhakar.jantagroceryadmin.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.prabhakar.jantagroceryadmin.Utils
import com.prabhakar.jantagroceryadmin.models.ProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class AdminViewModel : ViewModel() {

    private val _isImageUploaded = MutableStateFlow(false)
    var isImageUploaded: StateFlow<Boolean> = _isImageUploaded
    private val _downloadedUrls = MutableStateFlow<ArrayList<String?>>(arrayListOf())
    var downloadedUrls: StateFlow<ArrayList<String?>> = _downloadedUrls
    private val _isProductSaved = MutableStateFlow(false)
    var isProductSaved: StateFlow<Boolean> = _isProductSaved

    fun saveImageInDB(imagesUri: ArrayList<Uri>) {
        val downloadUrls = ArrayList<String?>()

        imagesUri.forEach { uri ->
            val imageRef = Utils.getUId()?.let { uid ->
                FirebaseStorage.getInstance().reference
                    .child(uid)
                    .child("images")
                    .child(UUID.randomUUID().toString())
            }
            imageRef?.putFile(uri)?.continueWithTask {
                imageRef?.downloadUrl
            }?.addOnCompleteListener { task ->
                val url = task.result
                downloadUrls.add(url.toString())

                if (downloadUrls.size == imagesUri.size) {
                    _isImageUploaded.value = true
                    _downloadedUrls.value = downloadUrls
                }
            }
        }


    }

    fun saveProduct(productModel: ProductModel) {
        FirebaseDatabase.getInstance().getReference("Admin")
            .child("All Products/${productModel.productRandomId}")
            .setValue(productModel)
            .addOnSuccessListener {
                FirebaseDatabase.getInstance().getReference("Admin")
                    .child("Products Category/${productModel.productRandomId}")
                    .setValue(productModel)
            }.addOnSuccessListener {
                FirebaseDatabase.getInstance().getReference("Admin")
                    .child("Products Type/${productModel.productRandomId}")
                    .setValue(productModel)
                    .addOnSuccessListener {
                        _isProductSaved.value = true
                    }
            }
    }
}