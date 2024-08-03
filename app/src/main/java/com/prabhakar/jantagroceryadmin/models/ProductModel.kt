package com.prabhakar.jantagroceryadmin.models

import java.util.UUID

data class ProductModel(
    var productRandomId: String? =null,
    var productName: String? = null,
    var productQuantity: Int? = null,
    var productUnit: String? = null,
    var productPrice: Int? = null,
    var productStock: Int? = null,
    var productCategory: String? = null,
    var productType: String? = null,
    var itemCount: Int? = null,
    var adminUid: String? = null,
    var productImageUris: ArrayList<String?>? = null,
)
