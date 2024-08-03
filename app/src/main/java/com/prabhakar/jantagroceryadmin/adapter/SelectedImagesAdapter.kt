package com.prabhakar.jantagroceryadmin.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.jantagroceryadmin.databinding.ItemLayoutSelectImageBinding

class SelectedImagesAdapter(private val imagesUris: ArrayList<Uri>) :
    RecyclerView.Adapter<SelectedImagesAdapter.SelectedImageViewHolder>() {
    class SelectedImageViewHolder(val binding: ItemLayoutSelectImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedImageViewHolder {
        val view =
            ItemLayoutSelectImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectedImageViewHolder(view)
    }


    override fun onBindViewHolder(holder: SelectedImageViewHolder, position: Int) {
        val uri = imagesUris[position]
//        holder.setData(uri, position, imagesUris)
//        notifyItemRemoved(position)

        holder.binding.apply {
            image.setImageURI(uri)
            btnClose.setOnClickListener {
                if (position < imagesUris.size) {
                    imagesUris.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return imagesUris.size
    }


}