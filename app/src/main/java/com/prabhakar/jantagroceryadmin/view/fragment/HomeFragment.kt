package com.prabhakar.jantagroceryadmin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.jantagroceryadmin.R
import com.prabhakar.jantagroceryadmin.adapter.CategoryAdapter
import com.prabhakar.jantagroceryadmin.databinding.FragmentHomeBinding
import com.prabhakar.jantagroceryadmin.models.CategoryModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val categoryList = arrayListOf<CategoryModel>()
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        buildList()
        setRecyclerView()
        return binding.root
    }

    private fun buildList() {
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

    private fun setRecyclerView() {
        adapter = CategoryAdapter(categoryList)
        val orientation: Int = RecyclerView.HORIZONTAL;
        binding.categoriesRecyclerView.layoutManager =LinearLayoutManager(requireContext(), orientation, false)
        binding.categoriesRecyclerView.adapter = adapter
    }

}