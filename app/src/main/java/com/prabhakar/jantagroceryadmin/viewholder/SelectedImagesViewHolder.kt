package com.prabhakar.jantagroceryadmin.viewholder

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.jantagroceryadmin.databinding.ItemLayoutSelectImageBinding

class SelectedImagesViewHolder(private val binding: ItemLayoutSelectImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setData(uri: Uri, position: Int, uriList: ArrayList<Uri>) {
        binding.image.setImageURI(uri)
        binding.btnClose.setOnClickListener {
            if (position < uriList.size) {
                uriList.removeAt(position)
            }
        }
    }
}